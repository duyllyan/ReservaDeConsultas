package br.com.duyllyan.reservaconsultas.database.exception;

import java.io.IOException;
import java.nio.file.FileSystemException;

public class DatabaseException extends RuntimeException {

    public DatabaseException(String msg) {
        super(msg);
    }
}
