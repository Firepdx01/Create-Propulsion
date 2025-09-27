package com.deltasf.createpropulsion.physics_assembler.packets;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;

public class GaugeInsertionErrorPacket {

    private final Text message;

    public GaugeInsertionErrorPacket(Text message) {
        this.message = message;
    }

    public GaugeInsertionErrorPacket(PacketByteBuf buf) {
        this.message = buf.readText();
    }

    public PacketByteBuf toPacketByteBuf() {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeText(message);
        return buf;
    }

    public void handle() {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.inGameHud != null) {
            mc.inGameHud.setOverlayMessage(message, false);
        }
    }
}