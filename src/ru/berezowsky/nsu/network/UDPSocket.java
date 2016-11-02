package ru.berezowsky.nsu.network;

import java.io.IOException;
import java.net.*;

public class UDPSocket extends DatagramSocket {

    private final InetAddress BROADCAST_ADDRESS = InetAddress.getByName("255.255.255.255");
    private final int port;

    public UDPSocket(int port) throws SocketException, UnknownHostException {
        super(port);
        this.port = port;
    }

    public void sendBroadcast(Object packet) throws IOException {
        byte[] bytesPacket = packet.toString().getBytes();
        send(new DatagramPacket(bytesPacket, bytesPacket.length, BROADCAST_ADDRESS, port));
    }
}
