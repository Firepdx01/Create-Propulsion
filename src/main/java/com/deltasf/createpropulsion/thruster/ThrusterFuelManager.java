package com.deltasf.createpropulsion.thruster;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.deltasf.createpropulsion.CreatePropulsion;
import com.deltasf.createpropulsion.network.PropulsionPackets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.JsonOps;

import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.registry.Registries;
import net.fabricmc.loader.api.FabricLoader;

import com.deltasf.createpropulsion.network.SyncThrusterFuelsPacket;

public class ThrusterFuelManager implements SimpleSynchronousResourceReloadListener {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    public static final String DIRECTORY = "thruster_fuels";

    private static Map<Fluid, FluidThrusterProperties> fuelPropertiesMap = new HashMap<>();
    public static final TagKey<Fluid> FORGE_FUEL_TAG = TagKey.of(Registries.FLUID.getKey(), new Identifier("c", "fuel"));

    public static Map<Fluid, FluidThrusterProperties> getFuelPropertiesMap() { 
        return fuelPropertiesMap; 
    }

    @Override
    public Identifier getFabricId() {
        return new Identifier(CreatePropulsion.ID, "thruster_fuels");
    }

    @Nullable
    @SuppressWarnings("deprecation")
    public static FluidThrusterProperties getProperties(Fluid fluid) {
        if (fluid == null || fluid == Fluids.EMPTY) return null;
        FluidThrusterProperties props = fuelPropertiesMap.get(fluid);
        if (props != null) {
            return props;
        }
        if (fluid.isIn(FORGE_FUEL_TAG)) return FluidThrusterProperties.DEFAULT;
        return null;
    }

    @Override
    public void reload(ResourceManager manager) {
        fuelPropertiesMap.clear();
        
        Map<Identifier, JsonElement> resources = new HashMap<>();
        manager.findResources(DIRECTORY, id -> id.getPath().endsWith(".json"))
            .forEach((id, resource) -> {
                try {
                    JsonElement json = GSON.fromJson(
                        new java.io.InputStreamReader(resource.getInputStream()), 
                        JsonElement.class
                    );
                    resources.put(id, json);
                } catch (Exception e) {
                    LOGGER.error("Failed to load thruster fuel from {}", id, e);
                }
            });
        
        fuelPropertiesMap = parseFuelProperties(resources);
        
        // Note: Client sync would need to be handled differently in Fabric
        // This would typically be done through a server resource reload event
    }

    public static void updateClient(Map<Identifier, FluidThrusterProperties> fuelMap) {
        Map<Fluid, FluidThrusterProperties> newClientMap = new HashMap<>();
        fuelMap.forEach((rl, props) -> {
            Fluid fluid = Registries.FLUID.get(rl);
            if (fluid != null) {
                newClientMap.put(fluid, props);
            }
        });
        fuelPropertiesMap = newClientMap;
    }

    private Map<Fluid, FluidThrusterProperties> parseFuelProperties(@Nonnull Map<Identifier, JsonElement> resources) {
        Map<Fluid, FluidThrusterProperties> newMap = new HashMap<>();

        for (Map.Entry<Identifier, JsonElement> entry : resources.entrySet()) {
            Identifier file = entry.getKey();
            JsonElement json = entry.getValue();

            ThrusterFuelDefinition.CODEC.parse(JsonOps.INSTANCE, json)
                .resultOrPartial(error -> {
                    LOGGER.error("[{}] Failed to parse thruster fuel definition from {}: {}", CreatePropulsion.ID, file, error);
                })
                .ifPresent(definition -> {
                    if (definition.requiredMod().isPresent() && !FabricLoader.getInstance().isModLoaded(definition.requiredMod().get())) {
                        return;
                    }
                    Fluid fluid = definition.getFluid();
                    if (fluid == Fluids.EMPTY) {
                        return;
                    }
                    FluidThrusterProperties properties = new FluidThrusterProperties(
                        definition.thrustMultiplier(), 
                        definition.consumptionMultiplier());
                    newMap.put(fluid, properties);
                });
        }
        
        return newMap;
    }
}