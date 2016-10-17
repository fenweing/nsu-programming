package ru.berezowsky.nsu.network;

import java.io.IOException;

public class FileListException extends IOException {
    public FileListException() {
        super();
    }

    public FileListException(String msg) {
        super(msg);
    }

    public FileListException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
