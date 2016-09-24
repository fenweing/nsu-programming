package ru.berezowsky.nsu.network.TCPSpeedTester.client;

import ru.berezowsky.nsu.network.TCPSpeedTester.Debugger;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main {
    public static void main(String[] args) {
        int port = 4444;
        int dataSize = 10000000;

        try {
            Socket socket = new Socket("localhost", port);
            OutputStream os = socket.getOutputStream();

            while (true){
                byte[] buf = new byte[dataSize];
                os.write(buf);
            }

        } catch (UnknownHostException ignored) {}
        catch (IOException e) {
            Debugger.log("Disconnected");
        }
    }
}
