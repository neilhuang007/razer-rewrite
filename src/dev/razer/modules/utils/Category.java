package dev.razer.modules.utils;

import dev.razer.util.font.Font;
import dev.razer.util.font.FontManager;
import lombok.Getter;

@Getter
public enum Category {
    // defining catagories
    COMBAT("combat",FontManager.getIconsOne(17), "a", 0x2),
    MOVEMENT("movement", FontManager.getIconsOne(17), "b", 0x3),
    PLAYER("player", FontManager.getIconsOne(17), "c", 0x4),
    RENDER("render", FontManager.getIconsOne(17), "g", 0x5),
    MECHANICS("mechanics", FontManager.getIconsOne(17), "a", 0x6),
    CHAT("chat", FontManager.getIconsOne(17), "f", 0x7);

    // name of category (in case we don't use enum names)
    private final String name;

    private final Font fontRenderer;

    // icon character
    private final String icon;

    // optional color for every specific category (module list or click gui)
    private final int color;

    Category(final String name, final Font fontRenderer, final String icon, final int color) {
        this.name = name;
        this.icon = icon;
        this.fontRenderer = fontRenderer;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public int getColor() {
        return color;
    }
}
