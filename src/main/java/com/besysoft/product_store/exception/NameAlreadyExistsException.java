package com.besysoft.product_store.exception;

public class NameAlreadyExistsException extends Exception {

    public NameAlreadyExistsException(String message) {
        super(message);
    }

    public NameAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

}
