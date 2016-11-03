package ru.berezowsky.nsu.network.TreeChat;

import java.net.DatagramPacket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.UUID;

public class Message {
    private final static String EMPTY_BODY = "";
    private static Charset utf8 = Charset.forName("UTF-8");

    private final MessageType type;
    private final Node from;
    private final String body;
    private final UUID uuid;


    private Message(MessageType type, Node from, String body){
        this(type, from, body, UUID.randomUUID());
    }

    private Message(MessageType type, Node from, String body, UUID uuid) {
        this.type = type;
        this.from = from;
        this.body = body;
        this.uuid = uuid;
    }

    public Node from() {
        return from;
    }
    public UUID getUuid() {
        return uuid;
    }
    public MessageType getType() {
        return type;
    }


    public static Message createOkMessage(Message message) {
        return new Message(MessageType.OK, null, EMPTY_BODY, message.uuid);
    }

    public static Message createConnectMessage() {
        return new Message(MessageType.CONNECT, null, EMPTY_BODY);
    }

    public static Message createTextMessage(String text){
        return new Message(MessageType.MESSAGE, null, text);
    }

    public DatagramPacket generatePacket(Node to) throws UnknownHostException {
        ByteBuffer buffer = ByteBuffer.allocate(Integer.MAX_VALUE);
        buffer.putInt(type.getCode());

        buffer.putLong(uuid.getMostSignificantBits());
        buffer.putLong(uuid.getLeastSignificantBits());

        byte[] bodyBytes = body.getBytes(utf8);
        buffer.putInt(bodyBytes.length);
        buffer.put(bodyBytes);

        return new DatagramPacket(buffer.array(), buffer.arrayOffset(), to.getAddress(), to.getPort());
    }

    public static Message createFromPacket(DatagramPacket packet) {
        ByteBuffer buffer = ByteBuffer.wrap(packet.getData());
        MessageType type = MessageType.generateByCode(buffer.getInt());

        long mostBits = buffer.getLong();
        long leastBits = buffer.getLong();
        UUID uuid = new UUID(mostBits, leastBits);

        int bodySize = buffer.getInt();
        String body;
        if (bodySize <= 0){
            body = EMPTY_BODY;
        } else{
            byte[] bodyBytes = new byte[bodySize];
            buffer.get(bodyBytes);
            body = new String(bodyBytes, utf8);
        }

        Node from = new Node(packet.getAddress().getHostAddress(), packet.getPort());

        return new Message(type, from, body, uuid);
    }


    @Override
    public boolean equals(Object o){
        return uuid == ((Message) o).uuid;
    }
}
