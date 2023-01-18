package com.alethio.orderservice.exceptions;

public class ResourceNotAvailableException extends Exception {
    public ResourceNotAvailableException(String msg) {
        super(msg);
    }

    public ResourceNotAvailableException(Throwable th) {
        super(th);
    }
}
