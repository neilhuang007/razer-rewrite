package dev.razer.ui.impl.standard.screen.impl;

import dev.razer.managers.RenderManager;
import dev.razer.ui.impl.standard.components.language.LanguageComponent;
import dev.razer.ui.impl.standard.screen.Screen;
import dev.razer.util.font.FontManager;
import dev.razer.util.gui.ScrollUtil;
import dev.razer.util.localization.Locale;
import dev.razer.util.localization.Localization;

import javax.vecmath.Vector2f;
import java.awt.*;
import java.util.ArrayList;

/**
 * @author Hazsi
 * @since 10/31/22
 */
public class LanguageScreen extends Screen {

    private final ArrayList<LanguageComponent> languages = new ArrayList<>();
    private final ScrollUtil scrollUtil = new ScrollUtil();

    public LanguageScreen() {
        for (Locale locale : Locale.values()) {
            languages.add(new LanguageComponent(locale, Localization.get("language_local", locale),
                    Localization.get("language_english", locale)));
        }
    }

    @Override
    public void onRender(int mouseX, int mouseY, float partialTicks) {
        scrollUtil.onRender();

        final Vector2f position = getStandardClickGUI().getPosition();
        final Vector2f scale = getStandardClickGUI().getScale();
        final double sidebar = getStandardClickGUI().getSidebar().sidebarWidth;

        for (int i = 0; i < this.languages.size(); i++) {
            this.languages.get(i).draw((i + 1) * 46 + scrollUtil.getScroll());
        }

        RenderManager.roundedRectangle(position.getX(), position.getY(), scale.x, 40, getStandardClickGUI().round, getStandardClickGUI().backgroundColor);
        RenderManager.rectangle(position.getX(), position.getY() + 20, scale.x, 20, getStandardClickGUI().backgroundColor);

//        FontManager.getIconsThree(28).drawString("4",
//                position.getX() + sidebar + 14, position.getY() + 16, -1);
        FontManager.getProductSansRegular(16).drawRightString(Localization.get("ui.language.text"),
                position.getX() + scale.getX() - 20, position.getY() + 20, new Color(255, 255, 255, 128).getRGB());

        scrollUtil.setMax(-2000);
    }

    @Override
    public void onClick(int mouseX, int mouseY, int mouseButton) {
        for (LanguageComponent component : this.languages) {
            component.click(mouseX, mouseY);
        }

        final Vector2f position = getStandardClickGUI().getPosition();
        final Vector2f scale = getStandardClickGUI().getScale();
        final double sidebar = getStandardClickGUI().getSidebar().sidebarWidth;

//        if (GUIUtil.mouseOver(position.getX() + sidebar + 4, position.getY() + 10, 40, 20, mouseX, mouseY)) {
//            getStandardClickGUI().selectedScreen = new InfoScreen();
//        }
    }
}