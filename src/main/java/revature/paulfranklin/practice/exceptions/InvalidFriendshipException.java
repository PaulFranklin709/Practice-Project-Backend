package revature.paulfranklin.practice.exceptions;

public class InvalidFriendshipException extends RuntimeException {
    public InvalidFriendshipException() {
    }

    public InvalidFriendshipException(String message) {
        super(message);
    }

    public InvalidFriendshipException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidFriendshipException(Throwable cause) {
        super(cause);
    }

    public InvalidFriendshipException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
