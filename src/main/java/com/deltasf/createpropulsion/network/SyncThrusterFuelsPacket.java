package com.deltasf.createpropulsion.network;

import java.util.HashMap;
import java.util.Map;

import com.deltasf.createpropulsion.thruster.FluidThrusterProperties;
import com.deltasf.createpropulsion.thruster.ThrusterFuelManager;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.fluid.Fluid;
import net.minecraft.registry.Registries;

public class SyncThrusterFuelsPacket {
    private final Map<Identifier, FluidThrusterProperties> fuelMap;

    public static SyncThrusterFuelsPacket create(Map<Fluid, FluidThrusterProperties> mapToSync) {
        Map<Identifier, FluidThrusterProperties> networkSafeMap = new HashMap<>();
        mapToSync.forEach((fluid, props) -> {
            Identifier key = Registries.FLUID.getId(fluid);
            if (key != null) {
                networkSafeMap.put(key, props);
            }
        });
        return new SyncThrusterFuelsPacket(networkSafeMap);
    }

    private SyncThrusterFuelsPacket(Map<Identifier, FluidThrusterProperties> fuelMap) {
        this.fuelMap = fuelMap;
    }

    public static SyncThrusterFuelsPacket fromPacketByteBuf(PacketByteBuf buf) {
        Map<Identifier, FluidThrusterProperties> map = buf.readMap(PacketByteBuf::readIdentifier, FluidThrusterProperties::decode);
        return new SyncThrusterFuelsPacket(map);
    }

    public PacketByteBuf toPacketByteBuf() {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeMap(this.fuelMap, PacketByteBuf::writeIdentifier, (b, props) -> props.encode(b));
        return buf;
    }

    public void handle() {
        ThrusterFuelManager.updateClient(this.fuelMap);
    }
}