package ru.berezowsky.nsu.network;

public class Debugger {
    private static boolean enabled = true;

    public static void log(Object msg){
        if (enabled){
            System.out.println(msg.toString());
        }
    }
}
