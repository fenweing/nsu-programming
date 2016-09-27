package ru.berezowsky.nsu.network.TCPSpeedTester.client;

import ru.berezowsky.nsu.network.TCPSpeedTester.Debugger;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        int port = 4444;
        String host = "localhost";
        int testSeconds = 20;

        try {
            Sender sender = new Sender(host, port);
            sender.testSpeed(testSeconds);
        } catch (IOException e) {
            Debugger.log("Something went wrong: " + e.getLocalizedMessage());
        }
    }
}
