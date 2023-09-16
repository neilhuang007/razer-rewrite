package dev.razer.module.impl.render;


import dev.razer.Razer;
import dev.razer.event.Listener;
import dev.razer.event.Priorities;
import dev.razer.event.annotations.EventLink;
import dev.razer.event.impl.client.Render2DEvent;
import dev.razer.event.impl.input.KeyboardInputEvent;
import dev.razer.module.Module;
import dev.razer.module.api.Category;
import dev.razer.module.api.ModuleInfo;
import dev.razer.util.render.RenderUtil;
import org.lwjgl.input.Keyboard;

import java.awt.*;

/**
 * Displays a GUI which can display and do various things
 *
 * @author Alan
 * @since 04/11/2021
 */
@ModuleInfo(name = "module.render.clickgui.name", description = "module.render.clickgui.description", category = Category.RENDER, keyBind = Keyboard.KEY_RSHIFT)
public final class ClickGUI extends Module {
    @Override
    public void onEnable() {
        Razer.eventBus.register(Razer.INSTANCE.getStandardClickGUI());
        mc.displayGuiScreen(Razer.INSTANCE.getStandardClickGUI());
//        Razer.INSTANCE.getStandardClickGUI().overlayPresent = null;
    }

    @Override
    public void onDisable() {
        Keyboard.enableRepeatEvents(false);
        Razer.eventBus.unregister(Razer.INSTANCE.getStandardClickGUI());
        Razer.INSTANCE.getExecutor().execute(() -> Razer.INSTANCE.getConfigFile().write());
    }

    @EventLink(value = Priorities.HIGH)
    public final Listener<Render2DEvent> onRender2D = event -> {
        double width = event.getScaledResolution().getScaledWidth();
        double height = event.getScaledResolution().getScaledHeight();

        UI_RENDER_RUNNABLES.add(() -> Razer.INSTANCE.getStandardClickGUI().render());
        UI_BLOOM_RUNNABLES.add(() -> Razer.INSTANCE.getStandardClickGUI().bloom());
        NORMAL_BLUR_RUNNABLES.add(() -> RenderUtil.rectangle(0, 0, width, height, Color.BLACK));
    };

    @EventLink()
    public final Listener<KeyboardInputEvent> onKey = event -> {

        if (event.getKeyCode() == this.getKeyCode()) {
            this.mc.displayGuiScreen(null);

            if (this.mc.currentScreen == null) {
                this.mc.setIngameFocus();
            }
        }
    };
}
