package com.deltasf.createpropulsion.registries;

import com.deltasf.createpropulsion.CreatePropulsion;
import com.deltasf.createpropulsion.lodestone_tracker.LodestoneTrackerBlock;
import com.deltasf.createpropulsion.magnet.RedstoneMagnetBlock;
import com.deltasf.createpropulsion.optical_sensors.InlineOpticalSensorBlock;
import com.deltasf.createpropulsion.optical_sensors.OpticalSensorBlock;
import com.deltasf.createpropulsion.physics_assembler.PhysicsAssemblerBlock;
import com.deltasf.createpropulsion.thruster.thruster.ThrusterBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.enums.Instrument;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class PropulsionBlocks {
    
    public static final ThrusterBlock THRUSTER_BLOCK = register("thruster", 
        new ThrusterBlock(FabricBlockSettings.create()
            .mapColor(MapColor.IRON_GRAY)
            .requiresTool()
            .sounds(BlockSoundGroup.METAL)
            .strength(5.5f, 4.0f)
            .nonOpaque()));
    
    public static final InlineOpticalSensorBlock INLINE_OPTICAL_SENSOR_BLOCK = register("inline_optical_sensor",
        new InlineOpticalSensorBlock(FabricBlockSettings.create()
            .mapColor(MapColor.YELLOW)
            .sounds(BlockSoundGroup.METAL)
            .strength(1.5f, 1.0f)
            .nonOpaque()));
    
    public static final OpticalSensorBlock OPTICAL_SENSOR_BLOCK = register("optical_sensor",
        new OpticalSensorBlock(FabricBlockSettings.create()
            .mapColor(MapColor.YELLOW)
            .sounds(BlockSoundGroup.METAL)
            .strength(2.5f, 2.0f)
            .nonOpaque()));
    
    public static final PhysicsAssemblerBlock PHYSICS_ASSEMBLER_BLOCK = register("physics_assembler",
        new PhysicsAssemblerBlock(FabricBlockSettings.create()
            .mapColor(MapColor.YELLOW)
            .sounds(BlockSoundGroup.METAL)
            .strength(2.5f, 2.0f)
            .nonOpaque()));
    
    public static final LodestoneTrackerBlock LODESTONE_TRACKER_BLOCK = register("lodestone_tracker",
        new LodestoneTrackerBlock(FabricBlockSettings.create()
            .mapColor(MapColor.YELLOW)
            .sounds(BlockSoundGroup.METAL)
            .strength(2.5f, 2.0f)
            .nonOpaque()));
    
    public static final RedstoneMagnetBlock REDSTONE_MAGNET_BLOCK = register("redstone_magnet",
        new RedstoneMagnetBlock(FabricBlockSettings.create()
            .mapColor(MapColor.RED)
            .sounds(BlockSoundGroup.METAL)
            .strength(2.5f, 2.0f)
            .nonOpaque()));
    
    private static <T extends Block> T register(String name, T block) {
        T registeredBlock = Registry.register(Registries.BLOCK, new Identifier(CreatePropulsion.ID, name), block);
        Registry.register(Registries.ITEM, new Identifier(CreatePropulsion.ID, name), 
            new BlockItem(registeredBlock, new FabricItemSettings()));
        return registeredBlock;
    }
    
    public static void register() {
        // This method is called to ensure the class is loaded
    }
}