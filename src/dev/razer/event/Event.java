package dev.razer.event;



public interface Event {
    default ScriptEvent<? extends Event> getScriptEvent() {
        return null;
    }
}
