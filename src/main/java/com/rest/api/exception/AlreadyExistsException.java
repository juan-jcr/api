package com.rest.api.exception;

/**
 * Excepción personalizada para indicar que un recurso ya existe.
 *
 * <p>Se lanza cuando se intenta crear o registrar un recurso que ya está presente
 * en el sistema, como un usuario con un nombre de usuario ya registrado.</p>
 */
public class AlreadyExistsException extends RuntimeException{
    public AlreadyExistsException(String message) {
        super(message);
    }
}
