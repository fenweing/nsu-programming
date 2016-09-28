package ru.berezowsky.nsu.network.UDPBroadcast;

import java.io.IOException;
import java.net.*;

public class UDPBroadcast {
    public static void main(String[] args) {
        int port = 5555;

        try {
            DatagramSocket socket = new DatagramSocket();

            socket.setBroadcast(true);

            InetAddress address = InetAddress.getByName("192.168.0.255");
            DatagramPacket packet = new DatagramPacket("helo".getBytes(), 4, address, 5555);
            socket.send(packet);



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
