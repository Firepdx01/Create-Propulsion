package com.deltasf.createpropulsion.registries;

import com.deltasf.createpropulsion.CreatePropulsion;
import com.deltasf.createpropulsion.optical_sensors.OpticalLensItem;
import com.deltasf.createpropulsion.physics_assembler.AssemblyGaugeItem;
import com.deltasf.createpropulsion.utility.BurnableItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class PropulsionItems {
    
    public static final BurnableItem PINE_RESIN = register("pine_resin", 
        new BurnableItem(new FabricItemSettings(), 1200));
    
    // Lenses
    public static final OpticalLensItem OPTICAL_LENS = register("optical_lens", 
        new OpticalLensItem(new FabricItemSettings()));
    public static final Item FLUID_LENS = register("fluid_lens", 
        new Item(new FabricItemSettings()));
    public static final Item FOCUS_LENS = register("focus_lens", 
        new Item(new FabricItemSettings()));
    public static final Item INVISIBILITY_LENS = register("invisibility_lens", 
        new Item(new FabricItemSettings()));
    public static final Item UNFINISHED_LENS = register("unfinished_lens", 
        new Item(new FabricItemSettings()));
    
    public static final AssemblyGaugeItem ASSEMBLY_GAUGE = register("assembly_gauge", 
        new AssemblyGaugeItem(new FabricItemSettings().maxCount(1)));
    
    public static final TagKey<Item> OPTICAL_LENS_TAG = makeTag("optical_lens");
    
    private static <T extends Item> T register(String name, T item) {
        return Registry.register(Registries.ITEM, new Identifier(CreatePropulsion.ID, name), item);
    }
    
    public static TagKey<Item> makeTag(String key) {
        return TagKey.of(Registries.ITEM.getKey(), new Identifier(CreatePropulsion.ID, key));
    }
    
    public static void register() {
        // This method is called to ensure the class is loaded
    }
}