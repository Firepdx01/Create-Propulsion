package com.deltasf.createpropulsion;

import com.deltasf.createpropulsion.events.FabricClientEvents;
import com.deltasf.createpropulsion.particles.ParticleTypes;
import com.deltasf.createpropulsion.registries.PropulsionBlockEntities;
import com.deltasf.createpropulsion.registries.PropulsionItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.DyeableItem;

public class CreatePropulsionClient implements ClientModInitializer {
    
    @Override
    public void onInitializeClient() {
        // Register client-side content
        PropulsionBlockEntities.registerRenderers();
        ParticleTypes.registerFactories();
        
        // Register render layers
        BlockRenderLayerMap.INSTANCE.putFluid(PropulsionFluids.TURPENTINE.get(), RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putFluid(PropulsionFluids.TURPENTINE.getFlowing(), RenderLayer.getTranslucent());
        
        // Register item colors
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            if (tintIndex != 0) return -1;
            DyeableItem dyeableItem = (DyeableItem) stack.getItem();
            if (dyeableItem.hasColor(stack)) {
                int color = dyeableItem.getColor(stack);
                
                float lerpCoefficient = 0.5f;
                int r = (color >> 16) & 0xFF;
                int g = (color >> 8) & 0xFF;
                int b = color & 0xFF;

                r = (int) (r + (255 - r) * lerpCoefficient);
                g = (int) (g + (255 - g) * lerpCoefficient);
                b = (int) (b + (255 - b) * lerpCoefficient);

                r = Math.max(0, Math.min(255, r));
                g = Math.max(0, Math.min(255, g));
                b = Math.max(0, Math.min(255, b));

                return (r << 16) | (g << 8) | b;
            } else {
                return 0x00FFFFFF;
            }
        }, PropulsionItems.OPTICAL_LENS.get());
        
        // Register client events
        FabricClientEvents.register();
    }
}