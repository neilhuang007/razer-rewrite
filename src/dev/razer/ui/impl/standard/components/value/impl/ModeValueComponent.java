package dev.razer.ui.impl.standard.components.value.impl;

import lombok.Getter;
import me.neilhuang007.razer.ui.click.standard.components.value.ValueComponent;
import me.neilhuang007.razer.util.gui.GUIUtil;
import me.neilhuang007.razer.util.vector.Vector2d;
import me.neilhuang007.razer.value.Mode;
import me.neilhuang007.razer.value.Value;
import me.neilhuang007.razer.value.impl.ModeValue;

@Getter
public class ModeValueComponent extends ValueComponent {

    public ModeValueComponent(final Value<?> value) {
        super(value);
    }

    @Override
    public void draw(final Vector2d position, final int mouseX, final int mouseY, final float partialTicks) {
        final ModeValue modeValue = (ModeValue) value;
        this.position = position;

        final String prefix = this.value.getName() + ":";

        this.nunitoSmall.drawString(prefix, this.position.x, this.position.y, this.getStandardClickGUI().fontDarkColor.hashCode());
        this.nunitoSmall.drawString(modeValue.getValue().getName(), this.position.x + this.nunitoSmall.width(prefix) + 2, this.position.y, this.getStandardClickGUI().fontDarkColor.hashCode());
    }

    @Override
    public void click(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.position == null) {
            return;
        }

        final ModeValue modeValue = (ModeValue) value;

        final boolean left = mouseButton == 0;
        final boolean right = mouseButton == 1;

        if (GUIUtil.mouseOver(this.position.x, this.position.y - 3.5f, getStandardClickGUI().width - 70, this.height, mouseX, mouseY)) {
            final int currentIndex = modeValue.getModes().indexOf(modeValue.getValue());

            Mode<?> mode = null;
            if (left) {
                if (modeValue.getModes().size() <= currentIndex + 1) {
                    mode = modeValue.getModes().get(0);
                } else {
                    mode = modeValue.getModes().get(currentIndex + 1);
                }
            } else if (right) {
                if (0 > currentIndex - 1) {
                    mode = modeValue.getModes().get(modeValue.getModes().size() - 1);
                } else {
                    mode = modeValue.getModes().get(currentIndex - 1);
                }
            }

            if (mode != null) {
                modeValue.update(mode);
            }
        }
    }

    @Override
    public void released() {

    }

    @Override
    public void bloom() {

    }

    @Override
    public void key(final char typedChar, final int keyCode) {

    }
}

