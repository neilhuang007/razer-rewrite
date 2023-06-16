package dev.razer.ui.component.button.impl;


import dev.razer.managers.ColorManager;
import dev.razer.managers.RenderManager;
import dev.razer.ui.component.button.MenuButton;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class MenuIconButton extends MenuButton {

    private final ResourceLocation resourceLocation;

    public MenuIconButton(double x, double y, double width, double height, Runnable runnable, ResourceLocation resourceLocation) {
        super(x, y, width, height, runnable);
        this.resourceLocation = resourceLocation;
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        // Runs the animation update - keep this
        super.draw(mouseX, mouseY, partialTicks);

        // Colors for rendering
        final double value = this.getY();
        final double progress = value / this.getY();
        final Color bloomColor = ColorManager.withAlpha(Color.BLACK, (int) (progress * 100));
        final Color fontColor = ColorManager.withAlpha(Color.WHITE, (int) (progress * (50 + this.getHoverAnimation().getValue() * 2)));

        // Renders the background of the button
        NORMAL_BLUR_RUNNABLES.add(() -> RenderManager.roundedRectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight(), 5, Color.WHITE));
        NORMAL_POST_BLOOM_RUNNABLES.add(() -> RenderManager.roundedRectangle(this.getX(), value, this.getWidth(), this.getHeight(), 5, bloomColor));

        // Renders the button icon TODO: Might change to font (depends on alan)
        UI_BLOOM_RUNNABLES.add(() -> {
            RenderManager.roundedRectangle(this.getX(), value, this.getWidth(), this.getHeight(), 5, ColorManager.withAlpha(Color.WHITE, (int) this.getHoverAnimation().getValue() / 3));
            RenderManager.roundedOutlineRectangle(this.getX(), value, this.getWidth(), this.getHeight(), 5, 0.5f, ColorManager.withAlpha(Color.WHITE, (int) this.getHoverAnimation().getValue() / 3));
            RenderManager.image(resourceLocation, this.getX() + this.getWidth() / 2.0F - 8, value + this.getHeight() / 2.0F - 8, 16, 16, fontColor);
        });
    }
}
