package org.example.exception;


public class NotMakeReviewException extends Exception{
    public NotMakeReviewException() {
    }

    public NotMakeReviewException(String message) {
        super(message);
    }

    public NotMakeReviewException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotMakeReviewException(Throwable cause) {
        super(cause);
    }

    public NotMakeReviewException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
