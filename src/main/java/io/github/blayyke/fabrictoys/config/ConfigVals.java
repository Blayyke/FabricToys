package io.github.blayyke.fabrictoys.config;

import blue.endless.jankson.Comment;
import net.minecraft.util.math.Vec3d;

public class ConfigVals {
    @Comment("Tweak that makes placed campfires not be lit by default.")
    public boolean enableCampfireTweak = true;

    @Comment("Tweak that makes players move slowly inside tall grass.")
    public boolean enableGrassSlowTweak = true;

    @Comment("Tweak that allows harvesting crops when right clicked.")
    public boolean enableCropHarvestTweak = true;

    @Comment("The amount to multiply the players velocity by when slowed by tall grass.")
    public Vec3d grassSlownessVec = new Vec3d(0.8, 1.0, 0.8);
}