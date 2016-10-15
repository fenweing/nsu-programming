package ru.berezowsky.nsu.network.FileTransfer.server;


import ru.berezowsky.nsu.network.Acceptor;
import ru.berezowsky.nsu.network.SocketHandler;

import java.io.IOException;

public class Server {
    public static void main(String[] args) {

        try {

            SocketHandler receiver = new FileReceiver();
            Acceptor acceptor = new Acceptor(4444, receiver);
            acceptor.start();



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
