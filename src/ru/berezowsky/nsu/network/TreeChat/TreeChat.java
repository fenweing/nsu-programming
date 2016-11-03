package ru.berezowsky.nsu.network.TreeChat;

import java.io.IOException;

public class TreeChat {
    public static void main(String[] args) {

        try{
//            Client client = new Client(4000);
            Client client = new Client(4001, "localhost", 4000);




        } catch ( InterruptedException | IOException e) {
            e.printStackTrace();
        }

    }
}
