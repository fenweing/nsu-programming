package ru.berezowsky.nsu.network.TreeChat;

public enum MessageType {
    OK, CONNECT, MESSAGE, DISCONNECT;

    public int getCode(){
        return ordinal();
    }

    public static MessageType generateByCode(int code){
        return MessageType.values()[code];
    }
}
