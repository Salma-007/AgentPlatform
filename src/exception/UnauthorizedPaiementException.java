package exception;

public class UnauthorizedPaiementException extends RuntimeException {
    public UnauthorizedPaiementException(String message) {
        super(message);
    }
}
