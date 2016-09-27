package ru.berezowsky.nsu.network.TCPSpeedTester;

public class Timer {

    private Thread timerThread = null;

    public void scheduleFinalAction(Runnable finalAction, int delay) {
        timerThread = new Thread(() -> {
            try {
                Thread.sleep(delay * 1000);
                finalAction.run();
            } catch (InterruptedException ignored) {}
        });
        timerThread.start();
    }

    public void schedulePeriodicAction(Runnable periodicAction, int period){
        schedulePeriodicAction(periodicAction, period, 0);
    }

    public void schedulePeriodicAction(Runnable periodicAction, int period, int duration) {
        timerThread = new Thread(() -> {
            if (duration > 0) {
                new Timer().scheduleFinalAction(timerThread::interrupt, duration);
            }
            try {
                while (!Thread.currentThread().isInterrupted()){
                        Thread.sleep(period * 1000);

                        periodicAction.run();
                }
            } catch (InterruptedException ignored) {}
        });
        timerThread.start();
    }

    public void interrupt(){
        timerThread.interrupt();
    }

    private void scheduleInterruptTimer(int delay){
        new Thread(() -> {
            try {
                Thread.sleep(delay * 1000);
                timerThread.interrupt();
            } catch (InterruptedException ignored) {}
        });
    }
}
