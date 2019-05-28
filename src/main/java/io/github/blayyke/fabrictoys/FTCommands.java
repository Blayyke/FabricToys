package io.github.blayyke.fabrictoys;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.io.File;
import java.nio.file.Files;

import static net.minecraft.server.command.CommandManager.literal;

public class FTCommands {
    private static File lootTablesDir = new File("loot_tables");
    private static File blocksDir = new File(lootTablesDir, "blocks");

    static {
        lootTablesDir.mkdirs();
        blocksDir.mkdir();
    }

    public static void init() {
        CommandRegistry registry = CommandRegistry.INSTANCE;
        LiteralArgumentBuilder<ServerCommandSource> configCmd = literal("createBasicLootTable")
                .executes(context -> {
                    try {
                        ServerPlayerEntity player = context.getSource().getPlayer();
                        ItemStack stack = player.getStackInHand(player.getActiveHand());
                        if (stack.isEmpty()) {
                            return 0;
                        }
                        if (Block.getBlockFromItem(stack.getItem()) == null) {
                            return 0;
                        }
                        Block block = Block.getBlockFromItem(stack.getItem());
                        Identifier id = Registry.BLOCK.getId(block);

                        File file = new File(blocksDir, id.getPath() + ".json");
                        file.createNewFile();
                        String str = "{\n" +
                                "  \"type\": \"minecraft:block\",\n" +
                                "  \"pools\": [\n" +
                                "    {\n" +
                                "      \"rolls\": 1,\n" +
                                "      \"entries\": [\n" +
                                "        {\n" +
                                "          \"type\": \"minecraft:item\",\n" +
                                "          \"name\": \"" + id + "\"\n" +
                                "        }\n" +
                                "      ],\n" +
                                "      \"conditions\": [\n" +
                                "        {\n" +
                                "          \"condition\": \"minecraft:survives_explosion\"\n" +
                                "        }\n" +
                                "      ]\n" +
                                "    }\n" +
                                "  ]\n" +
                                "}";
                        Files.write(file.toPath(), str.getBytes());

                        return 1;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return -1;
                    }
                });
        registry.register(false, serverCommandSourceCommandDispatcher -> {
            serverCommandSourceCommandDispatcher.register(configCmd);
        });
    }
}