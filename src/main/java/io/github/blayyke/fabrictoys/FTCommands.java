package io.github.blayyke.fabrictoys;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.github.blayyke.fabrictoys.accessor.ServerPlayerAccessor;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.registry.Registry;

import java.io.File;

public class FTCommands {
    private static File lootTablesDir = new File("loot_tables");
    private static File blocksDir = new File(lootTablesDir, "blocks");

    //Todo scrap all of this.
    //todo Essentials-like commands
    //todo ^ home cmds, spawn cmds, tp cmds.

    static {
        lootTablesDir.mkdirs();
        Registry.BIOME.get(0).getTemperature();
        blocksDir.mkdir();
    }

    public static void init() {
        CommandRegistry registry = CommandRegistry.INSTANCE;

        register(registry, LiteralArgumentBuilder.<ServerCommandSource>literal("home")
                .executes(context -> {
//                    return tryWithError(context, new HomeCommandExecutor(context));
                    return tryWithError(context, new CommandExecutor(context) {
                        @Override
                        public void execute() throws CommandSyntaxException {
                            ServerPlayerAccessor player = (ServerPlayerAccessor) getPlayer();
                            reply(new TextComponent("/home done!"));
                        }
                    });
                }));
    }

    public static void register(CommandRegistry registry, LiteralArgumentBuilder<ServerCommandSource> literal) {
        registry.register(false, dispatcher -> {
            dispatcher.register(literal);
        });
    }

    private static int tryWithError(CommandContext<ServerCommandSource> context, CommandExecutor commandExecutor) {
        try {
            commandExecutor.execute();
        } catch (Exception e) {
            context.getSource().sendError(new TranslatableComponent(Identifiers.MOD_ID + ".cmd.error", e.getMessage()));
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