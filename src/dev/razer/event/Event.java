package dev.razer.event;

import me.neilhuang007.razer.script.api.wrapper.impl.event.ScriptEvent;

public interface Event {
    default ScriptEvent<? extends Event> getScriptEvent() {
        return null;
    }
}
