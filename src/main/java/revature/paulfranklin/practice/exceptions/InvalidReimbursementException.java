package revature.paulfranklin.practice.exceptions;

import revature.paulfranklin.practice.entities.Reimbursement;

public class InvalidReimbursementException extends RuntimeException {
    public InvalidReimbursementException() {
    }

    public InvalidReimbursementException(String message) {
        super(message);
    }

    public InvalidReimbursementException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidReimbursementException(Throwable cause) {
        super(cause);
    }

    public InvalidReimbursementException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public static Reimbursement reimbursementNotFound() {
        throw new InvalidReimbursementException("Reimbursement was not found");
    }
}
