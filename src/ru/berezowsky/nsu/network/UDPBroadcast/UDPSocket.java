package ru.berezowsky.nsu.network.UDPBroadcast;

import java.io.IOException;
import java.net.*;

class UDPSocket extends DatagramSocket {

    private final InetAddress BROADCAST_ADDRESS = InetAddress.getByName("255.255.255.255");
    private final int port;

    UDPSocket(int port) throws SocketException, UnknownHostException {
        super(port);
        this.port = port;
    }

    void sendBroadcast(Object packet) throws IOException {
        byte[] bytesPacket = packet.toString().getBytes();
        send(new DatagramPacket(bytesPacket, bytesPacket.length, BROADCAST_ADDRESS, port));
    }
}
