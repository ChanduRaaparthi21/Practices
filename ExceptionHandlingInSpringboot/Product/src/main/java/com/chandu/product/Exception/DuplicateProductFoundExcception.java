package com.chandu.product.Exception;

public class DuplicateProductFoundExcception extends RuntimeException {

    public DuplicateProductFoundExcception(String message) {
        super(message);
    }
}
