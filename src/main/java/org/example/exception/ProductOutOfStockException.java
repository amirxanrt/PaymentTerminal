package org.example.exception;


public class ProductOutOfStockException extends Exception{
    public ProductOutOfStockException() {
    }

    public ProductOutOfStockException(String message) {
        super(message);
    }

    public ProductOutOfStockException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductOutOfStockException(Throwable cause) {
        super(cause);
    }

    public ProductOutOfStockException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
