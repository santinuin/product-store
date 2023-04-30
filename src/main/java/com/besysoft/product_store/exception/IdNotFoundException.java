package com.besysoft.product_store.exception;

public class IdNotFoundException extends Exception{

    public IdNotFoundException(String message) {
        super(message);
    }

    public IdNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
