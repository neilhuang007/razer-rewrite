package dev.razer.util.gui;


import dev.razer.managers.ColorManager;
import dev.razer.managers.RenderManager;
import dev.razer.util.Timers.StopWatch;
import dev.razer.util.interfaces.InstanceAccess;
import dev.razer.util.math.MathUtil;
import lombok.Getter;
import lombok.Setter;
import org.lwjgl.input.Mouse;

import javax.vecmath.Vector2d;
import java.awt.*;

@Getter
@Setter
public class ScrollUtil implements InstanceAccess {

    public double target, scroll, max = 25;
    public StopWatch stopwatch = new StopWatch();
    public StopWatch stopWatch2 = new StopWatch();
    public boolean scrollingIsAllowed;

    public void onRender() {

        //Sets target scroll every tick, this way scrolling will only change if there's less than 1 frame per tick
        if (stopWatch2.finished(50)) {
            final float wheel = Mouse.getDWheel();
            double stretch = 30;
            target = Math.min(Math.max(target + wheel / 2, max - (wheel == 0 ? 0 : stretch)), (wheel == 0 ? 0 : stretch));

            stopWatch2.reset();
        }

        //Moving render scroll towards target
        for (int i = 0; i < stopwatch.getElapsedTime(); ++i) {
            scroll = MathUtil.lerp(scroll, target, 1E-2F);
        }

        //resetting stopwatch
        stopwatch.reset();
    }

    public void renderScrollBar(Vector2d position, double maxHeight) {
        double percentage = getScroll() / getMax();
        double scrollBarHeight = maxHeight - ((getMax() / (getMax() - maxHeight)) * maxHeight);

        scrollingIsAllowed = scrollBarHeight < maxHeight;
        if (!scrollingIsAllowed) return;

        double scrollX = position.x;
        double scrollY = position.y + maxHeight * percentage - scrollBarHeight * percentage;
        Color color = ColorManager.withAlpha(Color.WHITE, 60);

        RenderManager.drawRoundedGradientRect(scrollX, scrollY, 1, scrollBarHeight, 0.5,
                color, color, true);
    }

    public void reset() {
        this.scroll = 0;
        this.target = 0;
    }
}
