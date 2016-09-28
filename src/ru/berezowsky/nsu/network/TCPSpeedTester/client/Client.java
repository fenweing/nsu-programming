package ru.berezowsky.nsu.network.TCPSpeedTester.client;

import ru.berezowsky.nsu.network.Debugger;

import java.io.IOException;

public class Client {
    public static void main(String[] args) {
        int port = 4444;
        String host = "192.168.0.101";
        int testSeconds = 20;

        try {
            Sender sender = new Sender(host, port);
            sender.testSpeed(testSeconds);
        } catch (IOException e) {
            Debugger.log("Something went wrong: " + e.getLocalizedMessage());
        }
    }
}