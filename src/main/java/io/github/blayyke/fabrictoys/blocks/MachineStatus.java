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