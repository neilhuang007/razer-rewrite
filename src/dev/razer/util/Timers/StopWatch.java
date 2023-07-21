package dev.razer.util.Timers;


public class StopWatch {
    private long millis;

    public StopWatch() {

        reset();
    }

    public boolean finished(long delay) {

        return (System.currentTimeMillis() - delay >= this.millis);
    }

    public void reset() {

        this.millis = System.currentTimeMillis();
    }

    public long getMillis() {

        return this.millis;
    }

    public void setMillis(long millis) {

        this.millis = millis;
    }

    public long getElapsedTime() {

        return System.currentTimeMillis() - this.millis;
    }
}
