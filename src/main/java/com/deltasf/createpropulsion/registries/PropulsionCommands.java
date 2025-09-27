package com.deltasf.createpropulsion.registries;

import com.deltasf.createpropulsion.CreatePropulsion;
import com.deltasf.createpropulsion.magnet.MagnetRegistry;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class PropulsionCommands {
    
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, 
                               net.minecraft.command.CommandRegistryAccess registryAccess,
                               net.minecraft.server.command.CommandManager.RegistrationEnvironment environment) {
        
        LiteralArgumentBuilder<ServerCommandSource> propulsionCommand = CommandManager.literal("propulsion")
            .requires(source -> source.hasPermissionLevel(2));

        propulsionCommand
            .then(CommandManager.literal("debug")
            .then(CommandManager.argument("value", BoolArgumentType.bool())
            .executes(PropulsionCommands::setDebugMode)));

        propulsionCommand
            .then(CommandManager.literal("clearMagnetRegistry")
            .executes(PropulsionCommands::clearMagnetRegistry));

        dispatcher.register(propulsionCommand);
    }

    private static int setDebugMode(CommandContext<ServerCommandSource> context) {
        boolean value = BoolArgumentType.getBool(context, "value");
        CreatePropulsion.debug = value;
        return 1;
    }

    private static int clearMagnetRegistry(CommandContext<ServerCommandSource> context) {
        MagnetRegistry.get().reset();
        return 1;
    }
}