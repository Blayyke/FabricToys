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

package io.github.blayyke.fabrictoys.config;

import blue.endless.jankson.Comment;

public class ConfigVals {
    @Comment("Tweak that makes placed campfires not be lit by default.")
    public boolean enableCampfireTweak = true;

    @Comment("The timeout that players are kicked after when a keepalive has not been received in the time period.\nThe default for vanilla is 15000.")
    public long keepaliveTimeout = 50000L;

    @Comment("Should the mod log when a player is timed out.")
    public boolean logOnTimeout = true;

    @Comment("Allow placing eggs on the ground for a 100% chicken spawn rate.")
    public boolean enableEggPlacement = true;

    @Comment("Show information about the item stored in the item frame you are looking at. (client only)")
    public boolean enableItemFrameInfo = true;

    @Comment("Should splash instant damage potions kill grass & plants it touches.")
    public boolean enablePotionTweak = true;
}