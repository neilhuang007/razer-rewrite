package dev.razer.event.types;

public class CancellableEvent implements Event {

    private boolean cancelled;

    public boolean isCancelled() { return cancelled; }

    public void setCancelled(boolean cancelled) { this.cancelled = cancelled; }

    public void cancel() {
        setCancelled(true);
    }

}
