package ru.berezowsky.nsu.network.FileTransfer.client;

import ru.berezowsky.nsu.network.Debugger;
import ru.berezowsky.nsu.network.FileList;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Client {
    private static Charset utf8 = Charset.forName("UTF-8");

    public static void main(String[] args) {
        try {
            int port = Integer.parseInt(args[1]);
            String host = args[0];
            String filename = args[2];
            File file = new File(filename);

            Socket socket = new Socket(host, port);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            if (!file.isDirectory()) {
                sendFile(filename, out);
            } else
            {
                sendDirectory(filename, out);
            }

        } catch (Exception e) {
            Debugger.log("something went wrong: " + e.getLocalizedMessage());
        }
    }

    private static void sendDirectory(String filename, DataOutputStream out) throws IOException {
        FileList list = new FileList(filename);

        for (File file : list){
            sendFile(file.getPath(), out);
        }
    }

    private static void sendFile(String filename, DataOutputStream out) {
        try {
            Debugger.log(filename + ": sending");

            byte[] fileBytes = new byte[16 * 1024];

            MessageDigest md = MessageDigest.getInstance("MD5");
            File file = new File(filename);

            DigestInputStream in = new DigestInputStream(new FileInputStream(file), md);

            out.writeInt(filename.getBytes(utf8).length);
            out.write(filename.getBytes(utf8));

            out.writeLong(file.length());

            int count;
            while ((count = in.read(fileBytes)) > 0) {
                out.write(fileBytes, 0, count);
            }

            out.write(md.digest());

            out.flush();

            Debugger.log(filename + ": sent");
        } catch (NoSuchAlgorithmException | IOException e) {
            Debugger.log(filename + ": cant send file (" + e.getLocalizedMessage() + ")");
        }
    }
}
