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

package io.github.blayyke.fabrictoys.util;

/**
 * All of the Auxiliary effects used in minecraft for World.spawnEvent
 */
public enum LevelEvents {
    DISPENSER_DISPENSE_BLOCK(1000),
    DISPENSER_FAIL(1001),
    DISPENSE_SHOOT_PROJECTILE(1002),
    LAUNCH_ENDER_PEAR(1003),
    LAUNCH_FIREWORKS_ROCKET(1004),
    RECORD_DROP(1005), IRON_DOOR_OPEN(1005),
    WOODEN_DOOR_OPEN(1006),
    WOODEN_TRAPDOOR_OPEN(1007),
    GATE_OPEN(1008),
    FIRE_EXTENGUISH(1009),
    PLAY_RECORD(1010),
    IRON_DOOR_SLAM(1011),
    WOODEN_DOOR_SLAM(1012),
    WOODEN_TRAPDOOR_SLAM(1013),
    FENCE_GATE_SWIVEL(1014),
    GHAST_SCREAM(1015),
    GHAST_SHOOT(1016),
    ENDERMAN_SCREAM(1017),
    FIRE_SHOOT(1018),
    DOOR_SWIVEL(1019), WOOD_DOOR_KNOCK(1019),
    REPAIR_ITEM(1020), IRON_DOOR_KNOCK(1020),
    DOOR_BROKEN(1021),
    WITHER_ATTACK(1022),
    WITHER_SHOOT(1024),
    ENTITY_TAKEOFF(1025),
    MOB_INFECT(1026),
    MOB_CURE(1027),
    ANVIL_DESTROY(1029),
    ANVIL_USE(1030),
    ANVIL_LAND(1031),
    PORTAL_WARP(1032),
    ORGANIC_WET(1033),
    ORGANIC_DRY(1034),
    BREW_POTION(1035),
    DOOR_CLOSE(1036),
    DOOR_OPEN(1037),

    DISPENSE_PARTICLES(2000),
    DESTROY_BLOCK(2001),
    XP_POP(2002), PROJECTILE_HIT(2002),
    EYE_OF_ENDER(2003),
    MOB_SPAWN(2004),
    BONEMEAL(2005),
    DRAGON_BREATH(2006),
    POTION_INSTANT(2007),
    DRAGON_DEFEATED(3000),
    DRAGON_ROARS(3001),

    UNKNOWN(0);

    private final int id;

    LevelEvents(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static LevelEvents fromId(int id) {
        for (LevelEvents i : values()) {
            if (i.id == id) {
                return i;
            }
        }
        return UNKNOWN;
    }
}