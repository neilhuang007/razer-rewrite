package dev.razer.ui.impl.standard.components.language;

import dev.razer.Razer;
import dev.razer.managers.RenderManager;
import dev.razer.util.font.FontManager;
import dev.razer.util.interfaces.InstanceAccess;
import dev.razer.util.localization.Locale;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.util.ResourceLocation;

import javax.vecmath.Vector2d;
import javax.vecmath.Vector2f;

/**
 * @author Hazsi
 * @since 10/31/22
 */
@Getter
@RequiredArgsConstructor
public class LanguageComponent implements InstanceAccess {
    private final Locale locale;
    private final String localName, englishName;

    private double lastY;

    public void draw(double y) {
        final Vector2f position = getStandardClickGUI().getPosition();
        final Vector2f scale = getStandardClickGUI().getScale();
        final double sidebar = getStandardClickGUI().getSidebar().sidebarWidth;

        RenderManager.roundedRectangle(position.getX() + sidebar + 8, position.getY() + y, 285,
                38, 6, getStandardClickGUI().getSidebarColor());

        // Draw locale english name
        FontManager.getNunito(20).drawString(this.englishName, position.getX() + sidebar + 18, position.getY() + y + 9,
                Razer.INSTANCE.getLocale().equals(this.locale) ? getTheme().getAccentColor(new Vector2d(0, position.y / 5)).getRGB() :
                        getStandardClickGUI().getFontColor().getRGB());

        // Draw locale native name
        FontManager.getNunito(17).drawString(this.localName, position.getX() + sidebar + 18,
                position.getY() + y + 24, ColorUtil.withAlpha(getStandardClickGUI().getFontColor(), 100).hashCode());

        // Draw flag
        RenderManager.image(new ResourceLocation("rise/icons/language/" + locale.getFile() + ".png"),
                position.getX() + sidebar + FontManager.getNunito(20).width(this.englishName) + 25, position.getY() + y + 5, 15, 15);

        this.lastY = y;
    }

    public void click(double mouseX, double mouseY) {
        final Vector2f position = getStandardClickGUI().getPosition();
        final Vector2f scale = getStandardClickGUI().getScale();
        final double sidebar = getStandardClickGUI().getSidebar().sidebarWidth;

        if (GUIUtil.mouseOver(position.getX() + sidebar + 8, position.getY() + lastY,
                285, 38, mouseX, mouseY)) {

            Razer.INSTANCE.setLocale(this.locale);
        }
    }
}