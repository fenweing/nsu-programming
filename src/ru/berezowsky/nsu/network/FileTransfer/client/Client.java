package ru.berezowsky.nsu.network.FileTransfer.client;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.Charset;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Client {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 4444;
        String filename = "test.txt";
        byte[] fileBytes = new byte[16 * 1024];
        Charset utf8 = Charset.forName("UTF-8");


        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            Socket socket = new Socket(host, port);

            File file = new File(filename);


            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DigestInputStream in = new DigestInputStream(new FileInputStream(file), md);

            out.writeInt(filename.getBytes(utf8).length);
            out.write(filename.getBytes(utf8));

            out.writeLong(file.length());

            int count;
            while ((count = in.read(fileBytes)) > 0){
                out.write(fileBytes, 0, count);
            }

            out.write(md.digest());

        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
