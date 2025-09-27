package com.deltasf.createpropulsion.events;

import com.deltasf.createpropulsion.physics_assembler.AssemblyGaugeItem;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class FabricClientEvents {
    
    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;
            
            // Handle assembly gauge left click
            while (client.options.attackKey.wasPressed()) {
                boolean wasHandled = AssemblyGaugeItem.handleLeftClick(client.player);
                if (wasHandled) {
                    break;
                }
            }
        });
    }
}