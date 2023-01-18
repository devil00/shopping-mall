package com.alethio.productwarehouseservice.exception;

public class MissingParamsException extends RuntimeException {
    public MissingParamsException(String msg) {
        super(msg);
    }

}
