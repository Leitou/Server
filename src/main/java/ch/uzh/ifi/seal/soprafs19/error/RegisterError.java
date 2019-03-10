package ch.uzh.ifi.seal.soprafs19.error;

public class RegisterError extends RuntimeException {
    public RegisterError(String message) {
        super(message);
    }
}
