package com.deltasf.createpropulsion.utility;

import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.item.Item;

public class BurnableItem extends Item {
    private final int burnTime;
    
    public BurnableItem(Properties properties, int burnTime) {
        super(properties);
        this.burnTime = burnTime;
        
        // Register as fuel in Fabric
        FuelRegistry.INSTANCE.add(this, burnTime);
    }
    
    public int getBurnTime() {
        return burnTime;
    }
}