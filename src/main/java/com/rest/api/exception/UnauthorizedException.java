package com.rest.api.exception;

/**
 * Excepción personalizada para indicar que el usuario no está autorizado para realizar la acción.
 *
 */
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
