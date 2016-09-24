package ru.berezowsky.nsu.network.TCPSpeedTester.server;

import ru.berezowsky.nsu.network.TCPSpeedTester.Debugger;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {
        int SLEEP_TIME = 1;
        int timePassed = 0;
        long totalBytes = 0;
        int port = 4444;

        try {
            ServerSocket serverSocket = new ServerSocket(port);

            Debugger.log("Waiting connection on port " + port);
            Socket socket = serverSocket.accept();
            Debugger.log("Connected. Waiting bytes");

            DataInputStream is = new DataInputStream(socket.getInputStream());

            while (true) {

                Thread.sleep(SLEEP_TIME * 1000);

                timePassed += SLEEP_TIME;

                int available = is.available();
                if (available == 0) {
                    throw new IOException("Disconnected");
                }

                byte[] buf = new byte[available];
                is.readFully(buf);

                totalBytes += available;

                Debugger.log("Speed: " + available/1024 + " Kb/s, Avg: " + totalBytes/(timePassed * 1024) + " Kb/s");
            }

        } catch (IOException e) {
            Debugger.log(e.getLocalizedMessage());
        } catch (InterruptedException ignored) {}
        finally {
            Debugger.log("Finished. Received: " + totalBytes/1024 + " Kb");
        }
    }
}
