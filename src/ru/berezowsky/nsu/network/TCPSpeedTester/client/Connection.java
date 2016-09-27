package ru.berezowsky.nsu.network.TCPSpeedTester.client;

import ru.berezowsky.nsu.network.TCPSpeedTester.Debugger;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class Connection extends Thread {
    private Socket socket;

    public Connection(String host, int port) throws IOException {
        socket = new Socket(host, port);
    }

    public void testSpeed(int seconds) throws IOException {
        new Timer().schedule(this::interrupt, seconds);
        this.start();
    }

    @Override
    public void run(){
        try {
            OutputStream os = socket.getOutputStream();

            while (true){
                byte[] buf = new byte[100000];
                os.write(buf);
                os.flush();
                sleep(0, 1);
            }
        } catch (IOException e) {
            Debugger.log("Something went wrong: " + e.getLocalizedMessage());
        } catch (InterruptedException e) {
            Debugger.log("Done");
        }


    }
}
