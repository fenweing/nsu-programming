package ru.berezowsky.nsu.network.FileTransfer.server;

import ru.berezowsky.nsu.network.Debugger;
import ru.berezowsky.nsu.network.SocketHandler;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.Charset;
import java.security.DigestOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class FileReceiver implements SocketHandler {

    @Override
    public void handle(Socket socket) {
        new ReceiverThread(socket).start();

    }

    private static class ReceiverThread extends Thread {

        Charset utf8 = Charset.forName("UTF-8");

        private final Socket socket;

        public ReceiverThread(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {

                DataInputStream socketInputStream = new DataInputStream(socket.getInputStream());
                DigestOutputStream fileOutputStream;

                int fileNameSize = socketInputStream.readInt();

                byte[] fileNameBytes = new byte[fileNameSize];
                socketInputStream.readFully(fileNameBytes);
                String fileName = new String(fileNameBytes, utf8);

                fileOutputStream = new DigestOutputStream(new FileOutputStream(fileName), MessageDigest.getInstance("MD5"));

                long fileSizeLeft = socketInputStream.readLong();
                while (fileSizeLeft > 0) {
                    byte[] buf;
                    if (fileSizeLeft > 1024) {
                        buf = new byte[1024];
                    } else {
                        buf = new byte[(int) fileSizeLeft];
                    }

                    int bytesRead = socketInputStream.read(buf);

                    if (bytesRead == -1){
                        break;
                    }

                    fileOutputStream.write(buf, 0, bytesRead);
                    fileSizeLeft -= bytesRead;
                }

                byte[] md5BytesRead = new byte[16];
                socketInputStream.readFully(md5BytesRead);

                byte[] md5BytesCalculated = fileOutputStream.getMessageDigest().digest();

                if (!Arrays.equals(md5BytesRead, md5BytesCalculated)){
                    throw new IOException("md5 check failed");
                }

            } catch (IOException | NoSuchAlgorithmException e) {
                Debugger.log(e.getLocalizedMessage());
            }

        }
    }
}
