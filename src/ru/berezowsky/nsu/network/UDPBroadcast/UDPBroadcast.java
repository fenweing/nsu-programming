package ru.berezowsky.nsu.network.UDPBroadcast;

import ru.berezowsky.nsu.network.Debugger;

import java.io.IOException;
import java.net.DatagramPacket;

public class UDPBroadcast {
    public static void main(String[] args) {
        int port = 5555;
        String packet = "packet";
        byte[] buf = new byte[10];
        DatagramPacket recvPacket = new DatagramPacket(buf, buf.length);

        try (UDPSocket socket = new UDPSocket(port)) {
            socket.sendBroadcast(packet);
            socket.receive(recvPacket);
            Debugger.log(recvPacket.getData());



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
