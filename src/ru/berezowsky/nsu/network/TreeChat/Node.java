package ru.berezowsky.nsu.network.TreeChat;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Node {
    private final int port;
    private final String host;

    public Node(String host, int port) {
        this.port = port;
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public String getHost() {
        return host;
    }

    public InetAddress getAddress() throws UnknownHostException {
        return InetAddress.getByName(host);
    }
}
