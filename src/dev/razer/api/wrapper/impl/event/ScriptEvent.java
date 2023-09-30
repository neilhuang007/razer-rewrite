package dev.razer.api.wrapper.impl.event;

import dev.razer.api.wrapper.ScriptWrapper;
import dev.razer.event.Event;

/**
 * @author Strikeless
 * @since 23.06.2022
 */
public abstract class ScriptEvent<T extends Event> extends ScriptWrapper<T> {

    public ScriptEvent(final T wrappedEvent) {
        super(wrappedEvent);
    }

    public abstract String getHandlerName();
}
