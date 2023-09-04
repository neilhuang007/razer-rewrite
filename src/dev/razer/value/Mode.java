package dev.razer.value;

import dev.razer.Razer;
import dev.razer.util.interfaces.InstanceAccess;
import dev.razer.util.interfaces.Toggleable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Rewritten from Patricks old version to be less retarded
 *
 * @author Hazsi
 * @since 10/10/2022
 */
@Getter
@RequiredArgsConstructor
public abstract class Mode<T> implements InstanceAccess, Toggleable {
    private final String name;
    private final T parent;
    private final List<Value<?>> values = new ArrayList<>();

    public final void register() {
        Razer.eventBus.register(this);
        this.onEnable();
    }

    public final void unregister() {
        Razer.eventBus.unregister(this);
        this.onDisable();
    }

    @Override
    public void toggle() {
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }


}