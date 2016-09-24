package ru.berezowsky.nsu.network.TCPSpeedTester.server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    private static int SLEEP_TIME = 1;

    public static void main(String[] args) {
        try {
            int timePassed = 0;
            int totalBytes = 0;
            int port = 4444;

            ServerSocket serverSocket = new ServerSocket(port);

            System.out.println("Waiting connection on port " + port);

            Socket socket = serverSocket.accept();

            System.out.println("Connected... waiting bytes");

            DataInputStream is = new DataInputStream(socket.getInputStream());

            while (true) {
                Thread.sleep(SLEEP_TIME * 1000);

                timePassed += SLEEP_TIME;

                int available = is.available();
                byte[] buf = new byte[available];

                is.readFully(buf);
                totalBytes += available;

                System.out.println("Speed: " + available/1024 + "Kb/s, Avg: " + totalBytes/(timePassed * 1024) + "Kb/s");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
