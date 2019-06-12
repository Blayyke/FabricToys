package io.github.blayyke.fabrictoys.accessor;

import net.minecraft.util.GlobalPos;

import java.util.List;

public interface ServerPlayerAccessor {
    List<GlobalPos> getHomes();
}