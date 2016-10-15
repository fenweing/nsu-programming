package ru.berezowsky.nsu.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Acceptor extends Thread {
    private final ServerSocket serverSocket;
    private final SocketHandler handler;

    Acceptor(int port, SocketHandler handler) throws IOException {
        super("Acceptor thread");
        serverSocket = new ServerSocket(port);
        this.handler = handler;
    }

    @Override
    public void run() {
        try {
            Debugger.log("Waiting for connections");

            while (!isInterrupted()){
                Socket socket = serverSocket.accept();

                Debugger.log(socket.getInetAddress() + ":" + socket.getPort() + " connected");

                handler.handle(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
