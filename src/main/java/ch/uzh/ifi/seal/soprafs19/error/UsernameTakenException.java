package ch.uzh.ifi.seal.soprafs19.error;

public class UsernameTakenException extends RuntimeException {
    public UsernameTakenException(String message){
        super(message);
    }
}
