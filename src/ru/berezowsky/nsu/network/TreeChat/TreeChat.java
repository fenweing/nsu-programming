package ru.berezowsky.nsu.network.TreeChat;

import ru.berezowsky.nsu.network.UDPSocket;

import java.net.SocketException;
import java.net.UnknownHostException;

public class TreeChat {
    public static void main(String[] args) {
        int port = 1234;
        boolean head = true;

        try {
            UDPSocket socket = new UDPSocket(port);

            while (true){

            }



        } catch (UnknownHostException | SocketException e) {
            e.printStackTrace();
        }

    }
}
