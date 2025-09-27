package com.deltasf.createpropulsion.registries;

import com.deltasf.createpropulsion.CreatePropulsion;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class PropulsionCreativeTab {
    
    public static final ItemGroup BASE_TAB = Registry.register(Registries.ITEM_GROUP,
        new Identifier(CreatePropulsion.ID, "base"),
        FabricItemGroup.builder()
            .displayName(Text.translatable("itemGroup.createpropulsion.base"))
            .icon(() -> new ItemStack(PropulsionBlocks.THRUSTER_BLOCK))
            .entries((displayContext, entries) -> {
                // From 0.1
                entries.add(PropulsionBlocks.INLINE_OPTICAL_SENSOR_BLOCK);
                entries.add(PropulsionBlocks.OPTICAL_SENSOR_BLOCK);
                entries.add(PropulsionBlocks.THRUSTER_BLOCK);
                // From 0.2
                entries.add(PropulsionBlocks.LODESTONE_TRACKER_BLOCK);
                entries.add(PropulsionBlocks.REDSTONE_MAGNET_BLOCK);
                entries.add(PropulsionBlocks.PHYSICS_ASSEMBLER_BLOCK);
                // From 0.2 (items)
                entries.add(PropulsionItems.ASSEMBLY_GAUGE);
                entries.add(PropulsionFluids.getBucket());
                entries.add(PropulsionItems.PINE_RESIN);
                entries.add(PropulsionItems.OPTICAL_LENS);
                entries.add(PropulsionItems.FLUID_LENS);
                entries.add(PropulsionItems.FOCUS_LENS);
                entries.add(PropulsionItems.INVISIBILITY_LENS);
            })
            .build());
    
    public static void register() {
        // This method is called to ensure the class is loaded
    }
}