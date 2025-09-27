package com.deltasf.createpropulsion.events;

import com.deltasf.createpropulsion.magnet.MagnetForceAttachment;
import com.deltasf.createpropulsion.magnet.MagnetRegistry;
import com.deltasf.createpropulsion.network.PropulsionPackets;
import com.deltasf.createpropulsion.network.SyncThrusterFuelsPacket;
import com.deltasf.createpropulsion.registries.PropulsionFluids;
import com.deltasf.createpropulsion.thruster.ThrusterFuelManager;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.valkyrienskies.core.api.ships.ServerShip;
import org.valkyrienskies.core.api.ships.Ship;
import org.valkyrienskies.mod.common.VSGameUtilsKt;

import java.util.HashMap;
import java.util.Map;

public class FabricEvents {
    
    public static void register() {
        // Server lifecycle events
        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            MagnetRegistry.get().reset();
        });
        
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            // Restore MagnetForceAttachment levels
            Map<Identifier, ServerWorld> levelLookup = new HashMap<>();
            for (ServerWorld level : server.getWorlds()) {
                levelLookup.put(level.getRegistryKey().getValue(), level);
            }

            ServerWorld overworld = server.getWorld(World.OVERWORLD);
            if (overworld == null) return;

            final String PREFIX = "minecraft:dimension:";

            var allShips = VSGameUtilsKt.getAllShips(overworld);
            for (Ship ship : allShips) {
                if (ship instanceof ServerShip sShip) {
                    var attachment = sShip.getAttachment(MagnetForceAttachment.class);
                    if (attachment == null) continue;
                    String shipDimensionId = sShip.getChunkClaimDimension();
                    if (shipDimensionId != null && shipDimensionId.startsWith(PREFIX)) {
                        String resourceLocationString = shipDimensionId.substring(PREFIX.length());
                        Identifier dimensionKey = new Identifier(resourceLocationString);
                        ServerWorld level = levelLookup.get(dimensionKey);
                        if (level != null) {
                            attachment.level = level;
                        }
                    }
                }
            }
        });
        
        // Player connection events
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayerEntity player = handler.getPlayer();
            PropulsionPackets.sendToPlayer(SyncThrusterFuelsPacket.create(ThrusterFuelManager.getFuelPropertiesMap()), player);
        });
        
        // Server tick events
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerWorld level : server.getWorlds()) {
                MagnetRegistry.forLevel(level).computePairs();
            }
        });
        
        // Block update events for turpentine-lava interaction
        // Note: This would need to be implemented using a custom block update listener
        // For now, we'll handle this in the fluid block classes
    }
}