package Persistance;

import java.io.IOException;

public class PersistanceException extends IOException {
    public PersistanceException(String message) {
        super(message);
    }
}
