package ru.berezowsky.nsu.network.TreeChat;

import ru.berezowsky.nsu.network.Debugger;
import ru.berezowsky.nsu.network.UDPSocket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.Scanner;

import static ru.berezowsky.nsu.network.TreeChat.MessageType.OK;

public class Client {
    private final UDPSocket socket;
    private Node parent = null;
    private final ArrayList<Node> nodes = new ArrayList<>();
    private final ArrayList<Message> uncheckedMessages = new ArrayList<>(); //TODO: manage with unlimited capacity
    private boolean connected = false;

    public Client(int port, String parentHost, int parentPort) throws IOException, InterruptedException {
        this.socket = new UDPSocket(port);

        if (parentHost != null){
            parent = new Node(parentHost, parentPort);
            connect();
        } else {
            connected = true;
        }

        //start periodic action to resend
        new Sender().start();
        receive();

    }

    public Client(int port) throws IOException, InterruptedException {
        this(port, null, 0);
    }

    private void receive() throws IOException, InterruptedException {
        while (connected){
            byte[] buf = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);

            Message message = Message.createFromPacket(packet);

            switch (message.getType()){
                case OK:
                    receiveOk(message);
                    break;

                case CONNECT:
                    receiveConnect(message);
                    break;

                case DISCONNECT:
                    //receiveDisconnect(message);
                    break;

                case MESSAGE:
                    Debugger.log(message.getUuid() + ": " + message.getBody());
                    replyOk(message);
                    sendAll(message, message.from());
                    break;
            }
        }
    }

    private void receiveConnect(Message message) throws IOException, InterruptedException {
        nodes.add(message.from());
        replyOk(message);
    }

    private void receiveOk(Message message) {
        uncheckedMessages.remove(message);
    }

    private void sendAll(Message message, Node avoid) throws IOException, InterruptedException {
        for (Node node : nodes){
            if (node != avoid){
                send(node, message);
                uncheckedMessages.add(message);
            }
        }
    }

    private void sendAll(Message message) throws IOException, InterruptedException {
        sendAll(message, null);
    }

    private void replyOk(Message message) throws IOException, InterruptedException {
        Message answer = Message.createOkMessage(message);
        send(message.from(), answer);
    }


    private void send(Node node, Message msg) throws IOException, InterruptedException {
        socket.send(msg.generatePacket(node));
    }

    private void connect() throws IOException, InterruptedException {
        if (parent != null) {
            while (!connected) {
                Message connectMsg = Message.createConnectMessage();
                send(parent, connectMsg);

                byte[] buf = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                Message reply = Message.createFromPacket(packet);
                if (reply.getType() == OK){
                    connected = true;
                    nodes.add(parent);
                }
            }

        } else {
            //throw //TODO: throw error "parent is null"
        }

    }

    class Sender extends Thread{
        @Override
        public void run() {
            try {
                Scanner scanner = new Scanner(System.in);
                while (true) {
                    String body = scanner.next();

                    Message msg = Message.createTextMessage(body);

                    sendAll(msg);
                }
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}
