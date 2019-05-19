package io.github.blayyke.fabrictoys;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.github.blayyke.fabrictoys.config.ConfigUpdateResult;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.ChatFormat;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import static net.minecraft.server.command.CommandManager.literal;

public class FTCommands {
    public static void init() {
        CommandRegistry registry = CommandRegistry.INSTANCE;
        LiteralArgumentBuilder<ServerCommandSource> configCmd = literal("ftconfig")
                .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2))
                .then(CommandManager.argument("key", StringArgumentType.word())
                        .then(CommandManager.argument("boolValue", BoolArgumentType.bool())
                                .executes(context -> {
                                    try {
                                        ServerPlayerEntity player = context.getSource().getPlayer();

                                        ConfigUpdateResult result = FabricToys.CONFIG.trySetValue(StringArgumentType.getString(context, "key"), BoolArgumentType.getBool(context, "boolValue"));
                                        if (result.getResult() == ConfigUpdateResult.Result.SUCCESS) {
                                            player.sendMessage(new TextComponent(result.getMessage()));
                                        } else if (result.getResult() == ConfigUpdateResult.Result.FAILURE) {
                                            TextComponent message = new TextComponent("Change failed: " + result.getMessage());
                                            message.setStyle(new Style().setColor(ChatFormat.RED));
                                            player.sendMessage(message);
                                        }
                                        return 0;
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        return -1;
                                    }
                                }))
                        .then(CommandManager.argument("strValue", StringArgumentType.word())
                                .executes(context -> {
                                    try {
                                        ServerPlayerEntity player = context.getSource().getPlayer();

                                        ConfigUpdateResult result = FabricToys.CONFIG.trySetValue(StringArgumentType.getString(context, "key"), StringArgumentType.getString(context, "value"));
                                        if (result.getResult() == ConfigUpdateResult.Result.SUCCESS) {
                                            player.sendMessage(new TextComponent("Change successful!"));
                                        } else if (result.getResult() == ConfigUpdateResult.Result.FAILURE) {
                                            TextComponent message = new TextComponent("Change failed: " + result.getMessage());
                                            message.setStyle(new Style().setColor(ChatFormat.RED));
                                            player.sendMessage(message);
                                        }
                                        return 0;
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        return -1;
                                    }
                                })));
        registry.register(false, serverCommandSourceCommandDispatcher -> {
            serverCommandSourceCommandDispatcher.register(configCmd);
            serverCommandSourceCommandDispatcher.register(configCmd);
        });
    }
}