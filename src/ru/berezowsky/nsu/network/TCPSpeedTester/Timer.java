package ru.berezowsky.nsu.network.TCPSpeedTester;

public class Timer {

    public static void scheduleFinalAction(Runnable finalAction, int delay) {
        new Thread(() -> {
            try {
                Thread.sleep(delay * 1000);
                finalAction.run();
                Debugger.log("Timer: final action invoked");
            } catch (InterruptedException ignored) {}
        }).start();
    }

    public static void schedulePeriodicAction(Runnable periodicAction, int period, int duration) {
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()){
                try {
                    scheduleFinalAction(() -> Thread.currentThread().interrupt(), duration);

                    Thread.sleep(period * 1000);

                    periodicAction.run();
                } catch (InterruptedException ignored) {}
            }
        }).start();
    }
}
