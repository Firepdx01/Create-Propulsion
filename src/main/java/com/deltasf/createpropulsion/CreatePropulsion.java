package com.deltasf.createpropulsion;

import com.deltasf.createpropulsion.network.PropulsionPackets;
import com.deltasf.createpropulsion.registries.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;
import net.minecraft.server.network.ServerPlayerEntity;

public class CreatePropulsion implements ModInitializer {
    public static final String ID = "createpropulsion";
    
    public static boolean debug = false;
    public static final boolean constDebug = false;

    @Override
    public void onInitialize() {
        // Register content
        PropulsionBlocks.register();
        PropulsionBlockEntities.register();
        PropulsionItems.register();
        PropulsionFluids.register();
        PropulsionCreativeTab.register();
        PropulsionPackets.register();
        
        // Register events
        registerEvents();
        
        // Register resource reload listeners
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new ThrusterFuelManager());
        
        // Register commands
        CommandRegistrationCallback.EVENT.register(PropulsionCommands::register);
    }
    
    private void registerEvents() {
        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            MagnetRegistry.get().reset();
        });
        
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayerEntity player = handler.getPlayer();
            PropulsionPackets.sendToPlayer(SyncThrusterFuelsPacket.create(ThrusterFuelManager.getFuelPropertiesMap()), player);
        });
    }
}