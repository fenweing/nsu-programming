package ru.berezowsky.nsu.network;

import java.net.Socket;

public interface SocketHandler {
    void handle(Socket socket);
}
