package dev.razer.ui.impl.standard.components.theme;

import dev.razer.managers.ColorManager;
import dev.razer.managers.RenderManager;
import dev.razer.ui.theme.Themes;
import dev.razer.util.animation.Animation;
import dev.razer.util.animation.Easing;
import dev.razer.util.font.FontManager;
import dev.razer.util.interfaces.InstanceAccess;
import dev.razer.util.render.RenderUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.vecmath.Vector3d;
import java.awt.*;

/**
 * @author Hazsi
 * @since 10/15/2022
 */
@Getter
@RequiredArgsConstructor
public class ThemeComponent implements InstanceAccess {
    private final Themes activeTheme;
    private final Animation xAnimation = new Animation(Easing.EASE_OUT_QUINT, 500);
    private final Animation yAnimation = new Animation(Easing.EASE_OUT_QUINT, 500);
    private final Animation opacityAnimation = new Animation(Easing.EASE_OUT_QUINT, 500);
    private final Animation selectorAnimation = new Animation(Easing.EASE_OUT_QUINT, 500);
    private Vector3d lastDraw = new Vector3d(0, 0, 0);

    public void draw(double yOffset, double width) {
        final int alpha = (int) opacityAnimation.getValue();

        final boolean active = this.activeTheme.equals(this.getTheme());
        final Color color = active ? new Color(15, 19, 26, (int) opacityAnimation.getValue()) :
                new Color(18, 21, 30, alpha);

        final double x = this.xAnimation.getValue();
        final double y = this.yAnimation.getValue() + yOffset;

        // This needs to be done in a runnable so that's its run AFTER the NORMAL_POST_BLOOM_RUNNABLES runnable

        // Draw background
        RenderManager.roundedRectangle(x, y, width, 50, 10, color);

        if (this.activeTheme.isTriColor()) {
            RenderManager.rectangle(x + (width / 2D) - 10, y + 0.5, 20, 39.5,
                    ColorManager.withAlpha(activeTheme.getSecondColor(), alpha));
            RenderManager.rectangle(x + (width / 2D) - 8, y, 16, 0.5,
                    ColorManager.withAlpha(activeTheme.getSecondColor(), (int) (alpha * 0.8)));
            RenderUtil.drawRoundedGradientRect(x, y, width / 2D, 40, 9,
                    ColorManager.withAlpha(activeTheme.getFirstColor(), alpha),
                    ColorManager.withAlpha(activeTheme.getSecondColor(), alpha), false);
            RenderUtil.drawRoundedGradientRect(x + (width / 2D) - 1, y, width / 2D + 1, 40, 9,
                    ColorManager.withAlpha(activeTheme.getSecondColor(), alpha),
                    ColorManager.withAlpha(activeTheme.getThirdColor(), alpha), false);
        } else {
            RenderUtil.drawRoundedGradientRect(x, y, width, 40, 9,
                    ColorManager.withAlpha(activeTheme.getFirstColor(), alpha),
                    ColorManager.withAlpha(activeTheme.getSecondColor(), alpha), false);
        }

        RenderManager.rectangle(x, y + 30, width, 10, color);

        FontManager.getProductSansRegular(16).drawCenteredString(activeTheme.getThemeName(),
                x + width / 2D, y + 37, active ? ColorManager.withAlpha(this.getTheme().getFirstColor(), alpha).getRGB() :
                        new Color(255, 255, 255, alpha).getRGB());

        // Render selector
        selectorAnimation.run(this.activeTheme.equals(getTheme()) ? 255 : 0);
        int selectorAlpha = (int) Math.min(selectorAnimation.getValue(), alpha);

        if (selectorAlpha > 0) {
            UI_BLOOM_RUNNABLES.add(() -> {
                if (this.activeTheme.isTriColor()) {
                    RenderManager.rectangle(x + (width / 2D) - 10, y, 20, 50,
                            ColorManager.withAlpha(activeTheme.getSecondColor(), selectorAlpha));
                    RenderUtil.drawRoundedGradientRect(x, y, width / 2D, 50, 12,
                            ColorManager.withAlpha(activeTheme.getFirstColor(), selectorAlpha),
                            ColorManager.withAlpha(activeTheme.getSecondColor(), selectorAlpha), false);
                    RenderUtil.drawRoundedGradientRect(x + (width / 2D) - 1, y, width / 2D, 50, 12,
                            ColorManager.withAlpha(activeTheme.getSecondColor(), selectorAlpha),
                            ColorManager.withAlpha(activeTheme.getThirdColor(), selectorAlpha), false);
                } else {
                    RenderUtil.drawRoundedGradientRect(x, y, width, 50, 12,
                            ColorManager.withAlpha(activeTheme.getFirstColor(), selectorAlpha),
                            ColorManager.withAlpha(activeTheme.getSecondColor(), selectorAlpha), false);
                }

                FontManager.getProductSansRegular(16).drawCenteredString(activeTheme.getThemeName(),
                        x + width / 2D, y + 37, ColorManager.withAlpha(activeTheme.getFirstColor(), selectorAlpha).getRGB());
            });
        }

        this.lastDraw = new Vector3d(x, y, width);
    }
}
