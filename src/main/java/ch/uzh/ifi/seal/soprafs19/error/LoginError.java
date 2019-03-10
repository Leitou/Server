package ch.uzh.ifi.seal.soprafs19.error;

public class LoginError extends RuntimeException {
    public LoginError(String message) {
        super(message);
    }
}
