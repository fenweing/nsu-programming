package ru.berezowsky.nsu.network.TCPSpeedTester.client;

import ru.berezowsky.nsu.network.Debugger;

import java.io.IOException;

public class Client {
    public static void main(String[] args) {
        try {
            int port = Integer.parseInt(args[1]);
            String host = args[0];
            int testSeconds = 20;


            Sender sender = new Sender(host, port);
            sender.testSpeed(testSeconds);
        } catch (Exception e) {
            Debugger.log("Something went wrong: " + e.getLocalizedMessage());
        }
    }
}
