package ru.berezowsky.nsu.network.TCPSpeedTester.client;

import ru.berezowsky.nsu.network.Debugger;
import ru.berezowsky.nsu.network.Timer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

class Sender extends Thread {
    private Socket socket;
    private int seconds;

    Sender(String host, int port) throws IOException {
        socket = new Socket(host, port);
    }

    void testSpeed(int seconds) throws IOException {
        this.seconds = seconds;
        new Timer().scheduleFinalAction(() -> {
            try {
                this.interrupt();
                socket.close();
            } catch (IOException ignored) {}
        }, seconds);
        start();
    }

    @Override
    public void run(){
        final int bufsize = 1024*1024;
        long kbytes = 0;
        try {
            OutputStream os = socket.getOutputStream();

            Debugger.log("Starting testing");
            while (!isInterrupted()){
                byte[] buf = new byte[bufsize];
                os.write(buf);
                os.flush();
                kbytes += bufsize / 1024;
            }
        } catch (IOException ignored) {}

        Debugger.log("Sent " + kbytes + " Kb " + "in " + seconds + "s. Speed: " + kbytes/seconds + " Kb/s");
    }
}
