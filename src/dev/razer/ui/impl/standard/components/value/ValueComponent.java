package dev.razer.ui.impl.standard.components.value;

import dev.razer.util.interfaces.InstanceAccess;
import dev.razer.value.Value;
import lombok.Getter;

import javax.vecmath.Vector2d;


@Getter
public abstract class ValueComponent implements InstanceAccess {

    public double height = 14;
    public Vector2d position;
    public Value<?> value;

    public ValueComponent(final Value<?> value) {
        this.value = value;
    }

    public abstract void draw(Vector2d position, int mouseX, int mouseY, float partialTicks);

    public abstract void click(int mouseX, int mouseY, int mouseButton);

    public abstract void released();

    public abstract void bloom();

    public abstract void key(final char typedChar, final int keyCode);
}
