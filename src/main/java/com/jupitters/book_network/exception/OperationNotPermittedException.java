package com.jupitters.book_network.exception;

public class OperationNotPermittedException extends RuntimeException{
    public OperationNotPermittedException(String s) {
        super(s);
    }
}
