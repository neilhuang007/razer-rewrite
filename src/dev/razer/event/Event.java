package dev.razer.event;


import dev.razer.gg.script.api.wrapper.impl.event.ScriptEvent;

public interface Event {
    default ScriptEvent<? extends Event> getScriptEvent() {
        return null;
    }
}
