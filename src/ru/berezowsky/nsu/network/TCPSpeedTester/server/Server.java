package ru.berezowsky.nsu.network.TCPSpeedTester.server;

import ru.berezowsky.nsu.network.Debugger;

import java.io.IOException;

public class Server {

    public static void main(String[] args) throws IOException {
        try {
            int port = Integer.parseInt(args[0]);
            new Acceptor(port).start();
        } catch (Exception e){
            Debugger.log("Something went wrong: " + e.getLocalizedMessage());
        }
    }
}
