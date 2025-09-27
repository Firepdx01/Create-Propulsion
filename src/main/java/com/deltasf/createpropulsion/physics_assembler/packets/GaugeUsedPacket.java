package com.deltasf.createpropulsion.physics_assembler.packets;

import com.deltasf.createpropulsion.physics_assembler.AssemblyGaugeOverlayRenderer;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.Box;

public class GaugeUsedPacket {
    private final Box selection;
    private MinecraftServer server;

    public GaugeUsedPacket(Box selection) {
        this.selection = selection;
    }

    public GaugeUsedPacket(PacketByteBuf buf) {
        this.selection = new Box(buf.readDouble(), buf.readDouble(), buf.readDouble(),
                                  buf.readDouble(), buf.readDouble(), buf.readDouble());
    }

    public PacketByteBuf toPacketByteBuf() {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeDouble(selection.minX);
        buf.writeDouble(selection.minY);
        buf.writeDouble(selection.minZ);
        buf.writeDouble(selection.maxX);
        buf.writeDouble(selection.maxY);
        buf.writeDouble(selection.maxZ);
        return buf;
    }

    public void handle() {
        AssemblyGaugeOverlayRenderer.triggerFlash(selection);
    }
    
    public MinecraftServer getServer() {
        return server;
    }
    
    public void setServer(MinecraftServer server) {
        this.server = server;
    }
}