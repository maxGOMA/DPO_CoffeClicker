package Business;

import java.io.IOException;

/**
 * BusinessException is a custom exception used to represent errors that occur
 * in the business logic layer of the application.
 * It typically wraps lower-level exceptions from the persistence layer and presents
 * them in a context relevant to the business domain.
 */
public class BusinessException extends IOException {

    /**
     * Constructs a new BusinessException with the specified detail message.
     * @param message the detail message describing the error
     */
    public BusinessException(String message) {
        super(message);
    }
}
