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

package io.github.blayyke.fabrictoys.blocks;

import io.github.blayyke.fabrictoys.Constants;

public enum MachineStatus {
    ACTIVE(Constants.MOD_ID + ".machine_status.active", Constants.GuiColors.GREEN),
    INACTIVE(Constants.MOD_ID + ".machine_status.inactive", Constants.GuiColors.GRAY),
    NO_FUEL(Constants.MOD_ID + ".machine_status.no_fuel", Constants.GuiColors.RED),
    NO_TOOL(Constants.MOD_ID + ".machine_status.no_tool", Constants.GuiColors.GRAY);

    private final String displayText;
    private final Integer colour;

    MachineStatus(String displayText, int colour) {
        this.displayText = displayText;
        this.colour = colour;
    }

    public String getDisplayText() {
        return displayText;
    }

    public Integer getDisplayColour() {
        return colour;
    }
}