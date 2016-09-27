package ru.berezowsky.nsu.network.TCPSpeedTester.server;

import ru.berezowsky.nsu.network.TCPSpeedTester.Debugger;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

class Receiver extends Thread{

    private final String info;
    private AtomicInteger totalBytes;
    private AtomicInteger currentBytes;
    private final Socket socket;

    Receiver(Socket socket) {
        this.socket = socket;
        this.info = socket.getInetAddress() + ":" + socket.getPort();
    }

    @Override
    public void run() {
        try {
            DataInputStream is = new DataInputStream(socket.getInputStream());

            int available = is.available();
            if (available == 0) {
                throw new IOException("Disconnected");
            }

            byte[] buf = new byte[available];
            is.readFully(buf);

            totalBytes.addAndGet(available);
            currentBytes.addAndGet(available);

        } catch (IOException e) {
            Debugger.log(e.getLocalizedMessage());
        }
    }
}
