package com.alethio.orderservice.exceptions;

public class APIException extends Exception {
    public APIException(String msg) {
        super(msg);
    }
    public APIException(Throwable th) {
        super(th);
    }
}
