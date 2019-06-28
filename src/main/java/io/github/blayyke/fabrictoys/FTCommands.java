/*
 *     This file is part of FabricToys.
 *
 *     FabricToys is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     FabricToys is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with FabricToys.  If not, see <https://www.gnu.org/licenses/>.
 */

package io.github.blayyke.fabrictoys;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.client.util.NetworkUtils;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

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
                            reply(new LiteralText("Cheats have been enabled.").formatted(Formatting.ITALIC, Formatting.GRAY));
                        } else {
                            reply(new LiteralText("This command is only for integrated servers.").formatted(Formatting.ITALIC, Formatting.RED));
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
            context.getSource().sendError(new TranslatableText(Constants.MOD_ID + ".cmd.error", e.getMessage()));
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

        public void reply(Text message) {
            ctx.getSource().sendFeedback(message, false);
        }

        public ServerPlayerEntity getPlayer() throws CommandSyntaxException {
            return ctx.getSource().getPlayer();
        }
    }
}