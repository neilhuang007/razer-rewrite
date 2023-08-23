package dev.razer.event;

@FunctionalInterface
public interface Listener<Event> {
    void call(Event event);
}