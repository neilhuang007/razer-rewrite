package dev.razer.ui.impl.standard.components.popup.impl;


import dev.razer.ui.impl.standard.components.popup.PopUp;

import javax.vecmath.Vector2f;

/**
 * Author: Alan
 * Date: 29/03/2022
 */

public class ModifierSelectionPopUp extends PopUp {
    @Override
    public void draw() {
        this.scale = new Vector2f(200, 120);
        super.draw();
    }
}
