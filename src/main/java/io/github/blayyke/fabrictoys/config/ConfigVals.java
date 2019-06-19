package io.github.blayyke.fabrictoys.config;

import blue.endless.jankson.Comment;
import net.minecraft.util.math.Vec3d;

public class ConfigVals {
    @Comment("Tweak that makes placed campfires not be lit by default.")
    public boolean enableCampfireTweak = true;

    @Comment("Tweak that allows harvesting crops when right clicked.")
    public boolean enableCropHarvestTweak = true;

    @Comment("The timeout that players are kicked after when a keepalive has not been received in the time period.\nThe default for vanilla is 15000.")
    public long keepaliveTimeout = 50000L;

    @Comment("Should the mod log when a player is timed out.")
    public boolean logOnTimeout = true;

    @Comment("Allow placing eggs on the ground for a 100% chicken spawn rate.")
    public boolean enableEggPlacement;
}