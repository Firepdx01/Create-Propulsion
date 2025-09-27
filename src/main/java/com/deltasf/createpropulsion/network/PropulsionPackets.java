package com.deltasf.createpropulsion.network;

import com.deltasf.createpropulsion.CreatePropulsion;
import com.deltasf.createpropulsion.physics_assembler.packets.GaugeInsertionErrorPacket;
import com.deltasf.createpropulsion.physics_assembler.packets.GaugeUsedPacket;
import com.deltasf.createpropulsion.physics_assembler.packets.ResetGaugePacket;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class PropulsionPackets {
    
    public static final Identifier SYNC_THRUSTER_FUELS = new Identifier(CreatePropulsion.ID, "sync_thruster_fuels");
    public static final Identifier GAUGE_USED = new Identifier(CreatePropulsion.ID, "gauge_used");
    public static final Identifier RESET_GAUGE = new Identifier(CreatePropulsion.ID, "reset_gauge");
    public static final Identifier GAUGE_INSERTION_ERROR = new Identifier(CreatePropulsion.ID, "gauge_insertion_error");
    
    public static void register() {
        // Register server-side packet handlers
        ServerPlayNetworking.registerGlobalReceiver(RESET_GAUGE, ResetGaugePacket::handle);
    }
    
    public static <MSG> void sendToPlayer(MSG message, ServerPlayerEntity player) {
        if (message instanceof SyncThrusterFuelsPacket packet) {
            ServerPlayNetworking.send(player, SYNC_THRUSTER_FUELS, packet.toPacketByteBuf());
        } else if (message instanceof GaugeInsertionErrorPacket packet) {
            ServerPlayNetworking.send(player, GAUGE_INSERTION_ERROR, packet.toPacketByteBuf());
        }
    }
    
    public static <MSG> void sendToAll(MSG message) {
        if (message instanceof GaugeUsedPacket packet) {
            for (ServerPlayerEntity player : PlayerLookup.all(packet.getServer())) {
                ServerPlayNetworking.send(player, GAUGE_USED, packet.toPacketByteBuf());
            }
        }
    }
    
    public static <MSG> void sendToServer(MSG message) {
        if (message instanceof ResetGaugePacket packet) {
            net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking.send(RESET_GAUGE, packet.toPacketByteBuf());
        }
    }
}