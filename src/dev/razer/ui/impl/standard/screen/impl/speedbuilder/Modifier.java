package dev.razer.ui.impl.standard.screen.impl.speedbuilder;

import dev.razer.managers.RenderManager;
import dev.razer.util.gui.GUIUtil;
import dev.razer.util.interfaces.InstanceAccess;
import dev.razer.util.vector.Vector2f;
import net.minecraft.client.Minecraft;

import java.awt.*;

/**
 * Author: Alan
 * Date: 28/03/2022
 */

public abstract class Modifier implements InstanceAccess {
    public Minecraft mc = Minecraft.getMinecraft();

    public String name;

    public double height = 30;

    Vector2f position;
    float width;
    Tick parent;

    public Modifier(final Tick parent) {
        this.parent = parent;
    }

    public abstract void move();

    public void render(final Vector2f position) {
        width = 307;
        final float height = (float) this.height;

        this.position = position;

        RenderManager.dropShadow(36, position.x, position.y, width, height, 50, 24);
        RenderManager.roundedRectangle(position.x, position.y, width, height, 11, new Color(0, 0, 0, 70));

        nunitoSmall.drawString(name, position.x + 8, position.y + 8, Color.WHITE.hashCode());

        nunitoSmall.drawStringWithShadow("-", position.x + width - 9, position.y + 6, Color.WHITE.hashCode());
    }

    public void click(final double mouseX, final double mouseY, final int button) {
        if (this.position == null) return;

        //Remove self
        if (GUIUtil.mouseOver(position.x + width - 10, position.y, 10, height, mouseX, mouseY) && button == 0) {
            parent.getModifiers().remove(this);
        }
    }
}
