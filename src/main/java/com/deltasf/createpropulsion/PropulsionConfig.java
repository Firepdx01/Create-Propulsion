package com.deltasf.createpropulsion;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = CreatePropulsion.ID)
public class PropulsionConfig implements ConfigData {
    
    @ConfigEntry.Category("thruster")
    @ConfigEntry.Gui.TransitiveObject
    public ThrusterConfig thruster = new ThrusterConfig();
    
    @ConfigEntry.Category("optical_sensors")
    @ConfigEntry.Gui.TransitiveObject
    public OpticalSensorConfig opticalSensors = new OpticalSensorConfig();
    
    @ConfigEntry.Category("redstone_magnet")
    @ConfigEntry.Gui.TransitiveObject
    public MagnetConfig magnet = new MagnetConfig();
    
    @ConfigEntry.Category("physics_assembler")
    @ConfigEntry.Gui.TransitiveObject
    public PhysicsAssemblerConfig physicsAssembler = new PhysicsAssemblerConfig();
    
    public static class ThrusterConfig {
        @ConfigEntry.Gui.Tooltip
        public double thrustMultiplier = 1.0;
        
        @ConfigEntry.Gui.Tooltip
        public double consumptionMultiplier = 1.0;
        
        @ConfigEntry.BoundedDiscrete(min = 10, max = 200)
        public int maxSpeed = 100;
        
        @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
        public int ticksPerUpdate = 10;
        
        public boolean damageEntities = true;
        
        public double particleOffsetIncomingVelModifier = 0.15;
        
        public double particleCountMultiplier = 1.0;
    }
    
    public static class OpticalSensorConfig {
        @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
        public int ticksPerUpdate = 2;
        
        @ConfigEntry.BoundedDiscrete(min = 4, max = 32)
        public int inlineMaxDistance = 16;
        
        @ConfigEntry.BoundedDiscrete(min = 8, max = 64)
        public int maxDistance = 32;
    }
    
    public static class MagnetConfig {
        public double powerMultiplier = 1.0;
        public double forceInducedTorqueMultiplier = 1.0;
    }
    
    public static class PhysicsAssemblerConfig {
        public int maxMinkDistance = 3;
    }
    
    // Static accessors for compatibility
    private static PropulsionConfig instance = new PropulsionConfig();
    
    public static void setInstance(PropulsionConfig config) {
        instance = config;
    }
    
    public static class ConfigValue<T> {
        private final T value;
        
        public ConfigValue(T value) {
            this.value = value;
        }
        
        public T get() {
            return value;
        }
    }
    
    // Static config values for compatibility
    public static final ConfigValue<Double> THRUSTER_THRUST_MULTIPLIER = new ConfigValue<>(1.0);
    public static final ConfigValue<Double> THRUSTER_CONSUMPTION_MULTIPLIER = new ConfigValue<>(1.0);
    public static final ConfigValue<Integer> THRUSTER_MAX_SPEED = new ConfigValue<>(100);
    public static final ConfigValue<Integer> THRUSTER_TICKS_PER_UPDATE = new ConfigValue<>(10);
    public static final ConfigValue<Boolean> THRUSTER_DAMAGE_ENTITIES = new ConfigValue<>(true);
    public static final ConfigValue<Double> THRUSTER_PARTICLE_OFFSET_INCOMING_VEL_MODIFIER = new ConfigValue<>(0.15);
    public static final ConfigValue<Double> THRUSTER_PARTICLE_COUNT_MULTIPLIER = new ConfigValue<>(1.0);
    
    public static final ConfigValue<Integer> OPTICAL_SENSOR_TICKS_PER_UPDATE = new ConfigValue<>(2);
    public static final ConfigValue<Integer> INLINE_OPTICAL_SENSOR_MAX_DISTANCE = new ConfigValue<>(16);
    public static final ConfigValue<Integer> OPTICAL_SENSOR_MAX_DISTANCE = new ConfigValue<>(32);
    
    public static final ConfigValue<Double> REDSTONE_MAGNET_POWER_MULTIPLIER = new ConfigValue<>(1.0);
    public static final ConfigValue<Double> REDSTONE_MAGNET_FORCE_INDUCED_TORQUE_MULTIPLIER = new ConfigValue<>(1.0);
    
    public static final ConfigValue<Integer> PHYSICS_ASSEMBLER_MAX_MINK_DISTANCE = new ConfigValue<>(3);
}