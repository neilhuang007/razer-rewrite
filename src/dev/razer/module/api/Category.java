package dev.razer.module.api;

import dev.razer.ui.impl.standard.screen.Screen;
import dev.razer.ui.impl.standard.screen.impl.CategoryScreen;
import dev.razer.ui.impl.standard.screen.impl.LanguageScreen;
import dev.razer.ui.impl.standard.screen.impl.SearchScreen;
import dev.razer.ui.impl.standard.screen.impl.ThemeScreen;
import dev.razer.util.font.Font;
import dev.razer.util.font.FontManager;
import lombok.Getter;


/**
 * @author Patrick
 * @since 10/19/2021
 */
@Getter
public enum Category {
    SEARCH("category.search", FontManager.getIconsThree(17), "U", 0x1, new SearchScreen()),
    COMBAT("category.combat", FontManager.getIconsOne(17), "a", 0x2, new CategoryScreen()),
    MOVEMENT("category.movement", FontManager.getIconsOne(17), "b", 0x3, new CategoryScreen()),
    PLAYER("category.player", FontManager.getIconsOne(17), "c", 0x4, new CategoryScreen()),
    RENDER("category.render", FontManager.getIconsOne(17), "g", 0x5, new CategoryScreen()),
    EXPLOIT("category.exploit", FontManager.getIconsOne(17), "a", 0x6, new CategoryScreen()),
    GHOST("category.ghost", FontManager.getIconsOne(17), "f", 0x7, new CategoryScreen()),
    OTHER("category.other", FontManager.getIconsOne(17), "e", 0x8, new CategoryScreen()),
    SCRIPT("category.script", FontManager.getIconsThree(17), "m", 0x7, new CategoryScreen()),

    THEME("category.themes", FontManager.getIconsThree(17), "U", 0xA, new ThemeScreen()),

    LANGUAGE("category.language", FontManager.getIconsThree(17), "U", 0xA, new LanguageScreen());


    public final Screen clickGUIScreen;
    // name of category (in case we don't use enum names)
    private final String name;
    // icon character
    private final String icon;
    // optional color for every specific category (module list or click gui)
    private final int color;
    private final Font fontRenderer;
//    public final Type clientType;

    Category(final String name, final Font fontRenderer, final String icon, final int color, final Screen clickGUIScreen) {
        this.name = name;
        this.icon = icon;
        this.color = color;
        this.clickGUIScreen = clickGUIScreen;
        this.fontRenderer = fontRenderer;
//        this.clientType = clientType;
    }

    Category(String name, Font fontRenderer, String icon, int color, CategoryScreen categoryScreen) {
        this.name = name;
        this.icon = icon;
        this.color = color;
        this.clickGUIScreen = clickGUIScreen;
        this.fontRenderer = fontRenderer;
    }

    public String getName() {
        return name;
    }

    public int getColor() {
        return color;
    }
}