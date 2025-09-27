package com.deltasf.createpropulsion.physics_assembler.packets;

import com.deltasf.createpropulsion.physics_assembler.AssemblyGaugeItem;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.item.ItemStack;

public class ResetGaugePacket {

    public ResetGaugePacket() {}

    public ResetGaugePacket(PacketByteBuf buf) {}

    public PacketByteBuf toPacketByteBuf() {
        return PacketByteBufs.create();
    }

    public static void handle(MinecraftServer server, ServerPlayerEntity player, 
                             ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        server.execute(() -> {
            ItemStack stack = player.getStackInHand(Hand.MAIN_HAND);
            if (stack.getItem() instanceof AssemblyGaugeItem) {
                AssemblyGaugeItem.resetPositions(stack, player);
            }
        });
    }
}