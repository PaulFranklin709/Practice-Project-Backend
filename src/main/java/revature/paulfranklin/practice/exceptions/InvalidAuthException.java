package revature.paulfranklin.practice.exceptions;

import revature.paulfranklin.practice.entities.User;

public class InvalidAuthException extends RuntimeException {
    public InvalidAuthException() {
    }

    public InvalidAuthException(String message) {
        super(message);
    }

    public InvalidAuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidAuthException(Throwable cause) {
        super(cause);
    }

    public InvalidAuthException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public static User userNotFound() {
        throw new InvalidAuthException("User was not found");
    }

    public static User friendNotFound() {
        throw new InvalidAuthException("Friend was not found");
    }
}
