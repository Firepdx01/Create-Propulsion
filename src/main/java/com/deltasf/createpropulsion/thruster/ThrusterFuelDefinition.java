package com.deltasf.createpropulsion.thruster;

import java.util.Optional;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.util.Identifier;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.registry.Registries;

public record ThrusterFuelDefinition (
    Identifier fluidId,
    float thrustMultiplier,
    float consumptionMultiplier,
    Optional<String> requiredMod
) {
    public static final Codec<ThrusterFuelDefinition> CODEC = RecordCodecBuilder.create(instance -> 
        instance.group(
            Identifier.CODEC.fieldOf("fluid").forGetter(ThrusterFuelDefinition::fluidId),
            Codec.FLOAT.fieldOf("thrust_multiplier").forGetter(ThrusterFuelDefinition::thrustMultiplier),
            Codec.FLOAT.fieldOf("consumption_multiplier").forGetter(ThrusterFuelDefinition::consumptionMultiplier),
            Codec.STRING.optionalFieldOf("required_mod").forGetter(ThrusterFuelDefinition::requiredMod)
        ).apply(instance, ThrusterFuelDefinition::new));

    
    public Fluid getFluid() {
        Fluid fluid = Registries.FLUID.get(this.fluidId);
        return fluid == null ? Fluids.EMPTY : fluid;
    }
}