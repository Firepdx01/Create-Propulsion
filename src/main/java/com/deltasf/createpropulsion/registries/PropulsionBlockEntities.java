package com.deltasf.createpropulsion.registries;

import com.deltasf.createpropulsion.CreatePropulsion;
import com.deltasf.createpropulsion.lodestone_tracker.LodestoneTrackerBlockEntity;
import com.deltasf.createpropulsion.lodestone_tracker.LodestoneTrackerRenderer;
import com.deltasf.createpropulsion.magnet.RedstoneMagnetBlockEntity;
import com.deltasf.createpropulsion.optical_sensors.InlineOpticalSensorBlockEntity;
import com.deltasf.createpropulsion.optical_sensors.OpticalSensorBlockEntity;
import com.deltasf.createpropulsion.optical_sensors.rendering.OpticalSensorRenderer;
import com.deltasf.createpropulsion.physics_assembler.PhysicsAssemblerBlockEntity;
import com.deltasf.createpropulsion.physics_assembler.PhysicsAssemblerRenderer;
import com.deltasf.createpropulsion.thruster.thruster.ThrusterBlockEntity;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class PropulsionBlockEntities {
    
    public static final BlockEntityType<ThrusterBlockEntity> THRUSTER_BLOCK_ENTITY = 
        Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(CreatePropulsion.ID, "thruster_block_entity"),
            FabricBlockEntityTypeBuilder.create(ThrusterBlockEntity::new, PropulsionBlocks.THRUSTER_BLOCK).build());
    
    public static final BlockEntityType<InlineOpticalSensorBlockEntity> INLINE_OPTICAL_SENSOR_BLOCK_ENTITY = 
        Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(CreatePropulsion.ID, "inline_optical_sensor_block_entity"),
            FabricBlockEntityTypeBuilder.create(InlineOpticalSensorBlockEntity::new, PropulsionBlocks.INLINE_OPTICAL_SENSOR_BLOCK).build());
    
    public static final BlockEntityType<OpticalSensorBlockEntity> OPTICAL_SENSOR_BLOCK_ENTITY = 
        Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(CreatePropulsion.ID, "optical_sensor_block_entity"),
            FabricBlockEntityTypeBuilder.create(OpticalSensorBlockEntity::new, PropulsionBlocks.OPTICAL_SENSOR_BLOCK).build());
    
    public static final BlockEntityType<PhysicsAssemblerBlockEntity> PHYSICS_ASSEMBLER_BLOCK_ENTITY =
        Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(CreatePropulsion.ID, "physics_assembler_block_entity"),
            FabricBlockEntityTypeBuilder.create(PhysicsAssemblerBlockEntity::new, PropulsionBlocks.PHYSICS_ASSEMBLER_BLOCK).build());
    
    public static final BlockEntityType<LodestoneTrackerBlockEntity> LODESTONE_TRACKER_BLOCK_ENTITY = 
        Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(CreatePropulsion.ID, "lodestone_tracker_block_entity"),
            FabricBlockEntityTypeBuilder.create(LodestoneTrackerBlockEntity::new, PropulsionBlocks.LODESTONE_TRACKER_BLOCK).build());
    
    public static final BlockEntityType<RedstoneMagnetBlockEntity> REDSTONE_MAGNET_BLOCK_ENTITY =
        Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(CreatePropulsion.ID, "redstone_magnet_block_entity"),
            FabricBlockEntityTypeBuilder.create(RedstoneMagnetBlockEntity::new, PropulsionBlocks.REDSTONE_MAGNET_BLOCK).build());
    
    public static void register() {
        // This method is called to ensure the class is loaded
    }
    
    public static void registerRenderers() {
        BlockEntityRendererRegistry.register(INLINE_OPTICAL_SENSOR_BLOCK_ENTITY, OpticalSensorRenderer::new);
        BlockEntityRendererRegistry.register(OPTICAL_SENSOR_BLOCK_ENTITY, OpticalSensorRenderer::new);
        BlockEntityRendererRegistry.register(PHYSICS_ASSEMBLER_BLOCK_ENTITY, PhysicsAssemblerRenderer::new);
        BlockEntityRendererRegistry.register(LODESTONE_TRACKER_BLOCK_ENTITY, LodestoneTrackerRenderer::new);
    }
}