package Persistance;

import java.io.IOException;

/**
 * PersistanceException is a custom exception used to indicate errors that occur
 * in the persistence layer of the application, such as file I/O issues,
 * database access errors, or serialization problems.
 */
public class PersistanceException extends IOException {

    /**
     * Constructs a new PersistanceException with the specified detail message.
     * @param message the detail message describing the error
     */
    public PersistanceException(String message) {
        super(message);
    }
}
