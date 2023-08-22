package dev.razer.event.bus.bus;

public interface Bus<Event> {

    void subscribe(final Object subscriber);
    void unsubscribe(final Object subscriber);

    void handle(Event event);

    void post(final Event event);

    void handle(Event event);
}
