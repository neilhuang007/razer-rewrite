package dev.razer.ui.impl.standard.components.theme;

import dev.razer.managers.RenderManager;
import dev.razer.ui.theme.Themes;
import dev.razer.util.animation.Animation;
import dev.razer.util.animation.Easing;
import dev.razer.util.interfaces.InstanceAccess;
import dev.razer.util.render.ColorUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.util.Vector3d;

import java.awt.*;

@Getter
@RequiredArgsConstructor
public class ThemeKeyColorComponent implements InstanceAccess {
    private final Themes.KeyColors color;
    private final Animation dimAnimation = new Animation(Easing.EASE_OUT_QUINT, 500);
    private final Animation bloomAnimation = new Animation(Easing.EASE_OUT_QUINT, 500);
    private Vector3d lastDraw = new Vector3d(0, 0, 0);

    public void draw(double x, double y, double width, boolean selected) {
        double value = dimAnimation.getValue();

        RenderManager.roundedRectangle(x, y, width, 17, 5, new Color(18, 21, 30));
        RenderManager.roundedRectangle(x + 0.5, y + 0.5, width - 1, 16, 4, color.getColor());

        RenderManager.roundedRectangle(x, y, width, 17, 5, new Color(25, 25, 25,
                (int) ((1 - dimAnimation.getValue()) * 128)));

        NORMAL_POST_BLOOM_RUNNABLES.add(() -> {
            RenderManager.roundedRectangle(x, y, width, 17, 5, new Color(18, 21, 30,
                    (int) (bloomAnimation.getValue() * 255)));
            RenderManager.roundedRectangle(x + 0.5, y + 0.5, width - 1, 16, 4,
                    ColorUtil.withAlpha(color.getColor(), (int) (bloomAnimation.getValue() * 255)));
        });

        this.lastDraw = new Vector3d(x, y, width);
    }
}