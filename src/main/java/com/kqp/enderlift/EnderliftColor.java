package com.kqp.enderlift;

import net.minecraft.block.MaterialColor;

public enum EnderliftColor {
    DEFAULT("", null),
    WHITE("white_", MaterialColor.WHITE),
    ORANGE("orange_", MaterialColor.ORANGE),
    MAGENTA("magenta_", MaterialColor.MAGENTA),
    LIGHT_BLUE("light_blue_", MaterialColor.LIGHT_BLUE),
    YELLOW("yellow_", MaterialColor.YELLOW),
    LIME("lime_", MaterialColor.LIME),
    PINK("pink_", MaterialColor.PINK),
    GRAY("gray_", MaterialColor.GRAY),
    LIGHT_GRAY("light_gray_", MaterialColor.LIGHT_GRAY),
    CYAN("cyan_", MaterialColor.CYAN),
    PURPLE("purple_", MaterialColor.PURPLE),
    BLUE("blue_", MaterialColor.BLUE),
    BROWN("brown_", MaterialColor.BROWN),
    GREEN("green_", MaterialColor.GREEN),
    RED("red_", MaterialColor.RED),
    BLACK("black_", MaterialColor.BLACK);

    public String name;
    public MaterialColor color;

    EnderliftColor(String name, MaterialColor color) {
        this.name = name;
        this.color = color;
    }
}
