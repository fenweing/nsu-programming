package ru.berezowsky.nsu.network.TCPSpeedTester.server;

import ru.berezowsky.nsu.network.TCPSpeedTester.Debugger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class Acceptor extends Thread {
    private final ServerSocket serverSocket;

    Acceptor(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    @Override
    public void run() {
        try {
            Debugger.log("Waiting for connections");

            while (!isInterrupted()){
                Socket socket = serverSocket.accept();

                Debugger.log(socket.getInetAddress() + ":" + socket.getPort() + " connected");

                new Receiver(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
