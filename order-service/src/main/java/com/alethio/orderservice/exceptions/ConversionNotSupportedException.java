package com.alethio.orderservice.exceptions;

public class ConversionNotSupportedException extends RuntimeException {
    public ConversionNotSupportedException(String msg) {
        super(msg);
    }
}
