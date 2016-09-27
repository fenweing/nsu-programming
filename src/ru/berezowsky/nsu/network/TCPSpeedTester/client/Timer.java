package ru.berezowsky.nsu.network.TCPSpeedTester.client;

import ru.berezowsky.nsu.network.TCPSpeedTester.Debugger;

public class Timer extends Thread {

    private Runnable handler;
    private int seconds;

    public void schedule(Runnable handler, int seconds) {
        this.handler = handler;
        this.seconds = seconds;
        start();
    }

    @Override
    public void run(){
        try {
            sleep(seconds * 1000);
            Debugger.log("Timer: executing command");
            handler.run();
        } catch (InterruptedException ignored) {}

    }
}
