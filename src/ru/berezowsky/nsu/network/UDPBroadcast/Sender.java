package ru.berezowsky.nsu.network.UDPBroadcast;

import ru.berezowsky.nsu.network.Timer;

import java.io.IOException;

class Sender {

    private final UDPSocket socket;
    private final Timer timer = new Timer();

    Sender(UDPSocket socket) {
        this.socket = socket;
    }

    void start() {
        String packet = "packet";
        timer.schedulePeriodicAction(() -> {
            try {
                socket.sendBroadcast(packet);
            } catch (IOException ignored) {}
        }, 1);
    }

    void interrupt(){
        timer.interrupt();
    }
}
