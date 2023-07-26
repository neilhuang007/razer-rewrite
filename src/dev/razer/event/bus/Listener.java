package dev.razer.event.bus;

@FunctionalInterface
public interface Listener<Event> { void call(Event event); }