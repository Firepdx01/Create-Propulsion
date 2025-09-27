package com.deltasf.createpropulsion.registries;

import com.deltasf.createpropulsion.CreatePropulsion;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class PropulsionFluids {
    
    public static final FlowableFluid TURPENTINE_STILL = register("turpentine", 
        new TurpentineFluid.Still());
    public static final FlowableFluid TURPENTINE_FLOWING = register("flowing_turpentine", 
        new TurpentineFluid.Flowing());
    
    public static final FluidBlock TURPENTINE_BLOCK = Registry.register(Registries.BLOCK, 
        new Identifier(CreatePropulsion.ID, "turpentine"), 
        new FluidBlock(TURPENTINE_STILL, net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings.create()
            .noCollision().strength(100.0f).dropsNothing()));
    
    public static final BucketItem TURPENTINE_BUCKET = Registry.register(Registries.ITEM, 
        new Identifier(CreatePropulsion.ID, "turpentine_bucket"),
        new BucketItem(TURPENTINE_STILL, new FabricItemSettings().recipeRemainder(Items.BUCKET).maxCount(1)));
    
    private static <T extends FlowableFluid> T register(String name, T fluid) {
        return Registry.register(Registries.FLUID, new Identifier(CreatePropulsion.ID, name), fluid);
    }
    
    public static FlowableFluid get() {
        return TURPENTINE_STILL;
    }
    
    public static FlowableFluid getFlowing() {
        return TURPENTINE_FLOWING;
    }
    
    public static BucketItem getBucket() {
        return TURPENTINE_BUCKET;
    }
    
    public static void register() {
        // This method is called to ensure the class is loaded
    }
    
    // Turpentine fluid implementation
    public static abstract class TurpentineFluid extends FlowableFluid {
        @Override
        public net.minecraft.fluid.Fluid getFlowing() {
            return TURPENTINE_FLOWING;
        }

        @Override
        public net.minecraft.fluid.Fluid getStill() {
            return TURPENTINE_STILL;
        }

        @Override
        protected boolean isInfinite(net.minecraft.world.World world) {
            return false;
        }

        @Override
        protected void beforeBreakingBlock(net.minecraft.world.WorldAccess world, net.minecraft.util.math.BlockPos pos, net.minecraft.block.BlockState state) {
            // Handle block breaking logic if needed
        }

        @Override
        protected int getFlowSpeed(net.minecraft.world.WorldView world) {
            return 7;
        }

        @Override
        protected int getLevelDecreasePerBlock(net.minecraft.world.WorldView world) {
            return 1;
        }

        @Override
        public net.minecraft.item.Item getBucketItem() {
            return TURPENTINE_BUCKET;
        }

        @Override
        protected boolean canBeReplacedWith(net.minecraft.fluid.FluidState state, net.minecraft.world.BlockView world, net.minecraft.util.math.BlockPos pos, net.minecraft.fluid.Fluid fluid, net.minecraft.util.math.Direction direction) {
            return direction == net.minecraft.util.math.Direction.DOWN && !fluid.isIn(net.minecraft.registry.tag.FluidTags.WATER);
        }

        @Override
        public int getTickRate(net.minecraft.world.WorldView world) {
            return 7;
        }

        @Override
        protected float getBlastResistance() {
            return 100.0f;
        }

        @Override
        public net.minecraft.block.BlockState toBlockState(net.minecraft.fluid.FluidState state) {
            return TURPENTINE_BLOCK.getDefaultState().with(net.minecraft.state.property.Properties.LEVEL_15, getBlockStateLevel(state));
        }

        @Override
        public boolean isStill(net.minecraft.fluid.FluidState state) {
            return false;
        }

        @Override
        public int getLevel(net.minecraft.fluid.FluidState state) {
            return 0;
        }

        public static class Flowing extends TurpentineFluid {
            @Override
            protected void appendProperties(net.minecraft.state.StateManager.Builder<net.minecraft.fluid.Fluid, net.minecraft.fluid.FluidState> builder) {
                super.appendProperties(builder);
                builder.add(LEVEL);
            }

            @Override
            public int getLevel(net.minecraft.fluid.FluidState state) {
                return state.get(LEVEL);
            }

            @Override
            public boolean isStill(net.minecraft.fluid.FluidState state) {
                return false;
            }
        }

        public static class Still extends TurpentineFluid {
            @Override
            public int getLevel(net.minecraft.fluid.FluidState state) {
                return 8;
            }

            @Override
            public boolean isStill(net.minecraft.fluid.FluidState state) {
                return true;
            }
        }
    }
}