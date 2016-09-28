package ru.berezowsky.nsu.network.TCPSpeedTester.server;

import java.io.IOException;

public class Server {

    public static void main(String[] args) throws IOException {
        new Acceptor(4444).start();
    }
}
