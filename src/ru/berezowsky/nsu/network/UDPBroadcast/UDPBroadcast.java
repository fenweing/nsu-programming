package ru.berezowsky.nsu.network.UDPBroadcast;

import ru.berezowsky.nsu.network.Debugger;
import ru.berezowsky.nsu.network.Timer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.Scanner;
import java.util.concurrent.SynchronousQueue;

public class UDPBroadcast {
    public static void main(String[] args) {
        try {
            int port;

            if (args.length != 1){
                System.out.print("port: ");
                port = new Scanner(System.in).nextInt();
            } else {
                port = Integer.parseInt(args[0]);
            }

            UDPSocket socket = new UDPSocket(port);

            Sender sender;
            Receiver receiver;


            receiver = new Receiver(socket);
            sender = new Sender(socket);

            sender.start();
            receiver.start();

        } catch (Exception e) {
            Debugger.log("Something went wrong: " + e.getLocalizedMessage());
        }
    }
}
