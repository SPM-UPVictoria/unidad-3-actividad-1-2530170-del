package com.astrea.core;

public class AstreaException extends Exception {

    public AstreaException(String mensaje) {
        super(mensaje);
    }

    public AstreaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}