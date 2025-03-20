package Business;

import java.io.IOException;

public class BusinessException extends IOException {
    public BusinessException(String message) {
        super(message);
    }
}
