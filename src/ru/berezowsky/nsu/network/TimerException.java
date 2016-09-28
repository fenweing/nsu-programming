package ru.berezowsky.nsu.network;

public class TimerException extends RuntimeException {
    public TimerException() {
        super();
    }

    public TimerException(String msg) {
        super(msg);
    }

    public TimerException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
