package com.ecomerce.user_service.exception;

public class DataExistException extends RuntimeException {
    public DataExistException() {
    }

    public DataExistException(String message) {
        super(message);
    }

    public DataExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataExistException(Throwable cause) {
        super(cause);
    }

    public DataExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
