package dev.razer.modules;

import dev.razer.Razer;
import dev.razer.event.impl.client.ModuleToggleEvent;
import dev.razer.modules.utils.Category;
import dev.razer.modules.utils.ModuleInfo;
import dev.razer.modules.utils.Settings;
import dev.razer.util.interfaces.InstanceAccess;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Module implements InstanceAccess {




    // basic module settings
    private String displayName;
    private ModuleInfo moduleInfo;
    private final Category category;
    private boolean autoEnabled;
    private boolean hidden;
    private boolean enabled;
    private int keyCode;

    private final String description;

    public final String getName() { return displayName; }

    public final String getDescription() { return description; }

    public final Category getCategory() { return category; }

    public final boolean isEnabled() { return enabled; }

    // basic module constructor here


    public Module() {
        if (this.getClass().isAnnotationPresent(ModuleInfo.class)) {
            this.moduleInfo = this.getClass().getAnnotation(ModuleInfo.class);
            this.category = moduleInfo.category();
            this.displayName = this.moduleInfo.name();
            this.keyCode = this.moduleInfo.keyBind();
            this.hidden = moduleInfo.hidden();
            this.description = moduleInfo.description();
        } else {
            throw new RuntimeException("ModuleInfo annotation not found on " + this.getClass().getSimpleName());
        }
    }

    public Module(final ModuleInfo info, String description, Category category) {
        this.moduleInfo = info;
        this.category = moduleInfo.category();
        this.description = this.moduleInfo.description();
        this.displayName = this.moduleInfo.name();
        this.keyCode = this.moduleInfo.keyBind();
    }


    // toggle and untoggle
    public void toggle() {
        this.setEnabled(enabled);
    }

    public void untoggle(){this.setEnabled(!enabled);}

    public void setEnabled(final boolean enabled) {
        if (this.enabled == enabled || (!this.moduleInfo.allowDisable() && !enabled)) {
            return;
        }

        this.enabled = enabled;

        Razer.eventBus.handle(new ModuleToggleEvent(this));

//        SoundUtil.toggleSound(enabled);

        if (enabled) {
            superEnable();
        } else {
            superDisable();
        }
    }

    /**
     * Called when a module gets enabled
     * -> important: whenever you override this method in a subclass
     * keep the super.onEnable()
     */
    public final void superEnable() {
        Razer.eventBus.subscribe(this);

        if (mc.thePlayer != null) this.onEnable();
    }

    /**
     * Called when a module gets disabled
     * -> important: whenever you override this method in a subclass
     * keep the super.onDisable()
     */
    public final void superDisable() {
        Razer.eventBus.unsubscribe(this);

        if (mc.thePlayer != null) this.onDisable();
    }

    protected void onEnable() {
    }

    protected void onDisable() {
    }

    public final boolean isHidden() { return hidden; }

    public final void setHidden(boolean hidden) { this.hidden = hidden; }

    public final String getDisplayName() { return displayName; }

    public final void setDisplayName(String newdisplayName) { displayName = newdisplayName; }

    // i don't know where to put this, so .....
    public Settings register(Settings setting) {
        Settings.settings.add(setting);
        return setting;
    }
}
