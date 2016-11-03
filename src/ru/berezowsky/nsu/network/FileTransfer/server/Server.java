package ru.berezowsky.nsu.network.FileTransfer.server;


import ru.berezowsky.nsu.network.Acceptor;
import ru.berezowsky.nsu.network.Debugger;
import ru.berezowsky.nsu.network.SocketHandler;

import java.io.IOException;

public class Server {
    public static void main(String[] args) {

        try {

            int port = Integer.parseInt(args[0]);

            SocketHandler receiver = new FileReceiver();
            Acceptor acceptor = new Acceptor(port, receiver);
            acceptor.start();

        } catch (IOException e) {
            Debugger.log("something went wrong: " + e.getLocalizedMessage());
        }
    }
}
