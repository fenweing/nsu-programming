package ru.berezowsky.nsu.network.TCPSpeedTester.server;

import ru.berezowsky.nsu.network.TCPSpeedTester.Debugger;
import ru.berezowsky.nsu.network.TCPSpeedTester.Timer;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicLong;

class Receiver extends Thread{

    private final String info;
    private AtomicLong totalBytes = new AtomicLong(0);
    private AtomicLong currentBytes = new AtomicLong(0);
    private int secondsPassed = 0;
    private final Socket socket;

    Receiver(Socket socket) {
        super("Receiver thread for " + socket.getInetAddress());
        this.socket = socket;
        this.info = socket.getInetAddress() + ":" + socket.getPort();
    }

    @Override
    public void run() {
        Timer timer = new Timer();
        timer.schedulePeriodicAction(() -> {
            secondsPassed += 1;
            Debugger.log(info + ":current speed: " + currentBytes.getAndSet(0)/1024 + " Kb/s, Avg: " + totalBytes.get()/(secondsPassed * 1024) + " Kb/s");
        }, 1);

        try {
            BufferedInputStream is = new BufferedInputStream(socket.getInputStream());

            while (!isInterrupted()) {
                byte[] buf = new byte[1024];
                int count = is.read(buf);

                if (count == -1) {
                    throw new IOException(info + ":disconnected");
                }

                totalBytes.addAndGet(count);
                currentBytes.addAndGet(count);
            }

        } catch (IOException e) {
            timer.interrupt();
            Debugger.log(e.getLocalizedMessage());
        }
    }
}
