package io.github.blayyke.fabrictoys;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.ChatFormat;
import net.minecraft.client.util.NetworkUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.server.network.ServerPlayerEntity;

public class FTCommands {
    public static void init() {
        CommandRegistry registry = CommandRegistry.INSTANCE;

        register(registry, LiteralArgumentBuilder.<ServerCommandSource>literal("lan")
                .executes(context -> executeCatching(context, new CommandExecutor(context) {
                    @Override
                    public void execute() throws CommandSyntaxException {
                        MinecraftServer server = getPlayer().server;
                        if (server instanceof IntegratedServer) {
                            server.openToLan(server.getDefaultGameMode(), true, NetworkUtils.findLocalPort());
                            reply(new TextComponent("Cheats have been enabled.").applyFormat(ChatFormat.ITALIC, ChatFormat.GRAY));
                        } else {
                            reply(new TextComponent("This command is only for integrated servers.").applyFormat(ChatFormat.ITALIC, ChatFormat.RED));
                        }
                    }
                })).requires((serverCommandSource -> serverCommandSource.hasPermissionLevel(0))));
    }

    public static void register(CommandRegistry registry, LiteralArgumentBuilder<ServerCommandSource> literal) {
        registry.register(false, dispatcher -> {
            dispatcher.register(literal);
        });
    }

    private static int executeCatching(CommandContext<ServerCommandSource> context, CommandExecutor commandExecutor) {
        try {
            commandExecutor.execute();
        } catch (Exception e) {
            context.getSource().sendError(new TranslatableComponent(Constants.MOD_ID + ".cmd.error", e.getMessage()));
            e.printStackTrace();
        }
        return 0;
    }

    private static abstract class CommandExecutor {
        private final CommandContext<ServerCommandSource> ctx;

        public CommandExecutor(CommandContext<ServerCommandSource> context) {
            this.ctx = context;
        }

        public abstract void execute() throws CommandSyntaxException;

        public void reply(Component message) {
            ctx.getSource().sendFeedback(message, false);
        }

        public ServerPlayerEntity getPlayer() throws CommandSyntaxException {
            return ctx.getSource().getPlayer();
        }
    }
}