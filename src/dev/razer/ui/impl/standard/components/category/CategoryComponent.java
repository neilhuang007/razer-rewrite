package dev.razer.ui.impl.standard.components.category;


import dev.razer.Razer;
import dev.razer.managers.ColorManager;
import dev.razer.managers.RenderManager;
import dev.razer.module.api.Category;
import dev.razer.ui.impl.standard.RiseClickGUI;
import dev.razer.ui.impl.standard.screen.Screen;
import dev.razer.util.animation.Animation;
import dev.razer.util.font.FontManager;
import dev.razer.util.interfaces.InstanceAccess;
import dev.razer.util.localization.Localization;
import net.minecraft.client.renderer.GlStateManager;

import javax.vecmath.Vector2d;
import java.awt.*;

import static dev.razer.util.animation.Easing.LINEAR;


public final class CategoryComponent implements InstanceAccess {


    public final Category category;
    private final Animation animation = new Animation(LINEAR, 500);
    private long lastTime = 0;
    private double selectorOpacity;

    private float x, y;
    private boolean down;

    public CategoryComponent(final Category category) {
        this.category = category;
    }

    public void render(final double offset, final double sidebarWidth, final double opacity, final Screen selectedScreen) {
        final RiseClickGUI clickGUI = Razer.INSTANCE.getStandardClickGUI();

        if (System.currentTimeMillis() - lastTime > 300) lastTime = System.currentTimeMillis();
        final long time = System.currentTimeMillis();

        /* Gets position depending on sidebar animation */
        x = (float) (clickGUI.position.x - (69 - sidebarWidth) - 21);
        y = (float) (clickGUI.position.y + offset) + 16;

        /* Animations */
        animation.setDuration(200);
        animation.run(selectedScreen.equals(category.getClickGUIScreen()) ? 255 : 0);

        final double spacer = 4;
        final double width = nunitoSmall.width(Localization.get(category.getName())) + spacer + category.getFontRenderer().width(category.getIcon());

        double scale = 0.5;
        GlStateManager.pushMatrix();
//        GlStateManager.translate(x, y, 0);
//        GlStateManager.scale(scale, scale, 1);

        /* Draws selection */
        RenderManager.roundedRectangle(x + 1.5, y - 5.5, width + 9, 15, 4,
                ColorManager.withAlpha(getTheme().getAccentColor(new Vector2d(0, y / 5D)), (int) (Math.min(animation.getValue(), opacity))).darker());

        /* Draws category icon */
//        this.icon.drawString(category.getIcon(), (float) (x + selectorOpacity / 80f - 8), y - 2, color);

        /* Draws category name */
//        if (selectedScreen.equals(category.getClickGUIScreen())) {
//        clickGUI.nunitoSmall.drawString(category.getName(), (float) (x + selectorOpacity / 80f + 5), y + 1, new Color(0, 0, 0, Math.min(100, (int) opacity)).hashCode());
//        }

        int color = new Color(255, 255, 255, Math.min(selectedScreen.equals(category.getClickGUIScreen()) ? 255 : 200, (int) opacity)).hashCode();

        category.getFontRenderer().drawString(category.getIcon(), (float) (x + animation.getValue() / 80f + 3), y, color);

        clickGUI.nunitoSmall.drawString(Localization.get(category.getName()), (float) (x + animation.getValue() / 80f + 3 + spacer) +
                FontManager.getIconsOne(17).width(category.getIcon()), y, color);

        GlStateManager.popMatrix();

        lastTime = time;
    }

    public void click(final float mouseX, final float mouseY, final int button) {
        final boolean left = button == 0;
        if (GUIUtil.mouseOver(x - 11, y - 5, 70, 22, mouseX, mouseY) && left) {
            this.getStandardClickGUI().switchScreen(this.category);
            down = true;
        }
    }

    public void bloom(final double opacity) {
        final double width = nunitoSmall.width(Localization.get(category.getName())) + 12;
        RenderManager.roundedRectangle(x + 1.5, y - 5.5, width + 9, 15, 4,
                ColorManager.withAlpha(getTheme().getAccentColor(new Vector2d(0, y / 5D)), (int) (Math.min(animation.getValue(), opacity))).darker());
    }

    public void release() {
        down = false;
    }


}