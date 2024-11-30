package com.rest.api.exception.advice;

import com.rest.api.exception.AlreadyExistsException;
import com.rest.api.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Manejador global de excepciones para la aplicación.
 *
 * <p>Intercepta excepciones específicas lanzadas en los controladores y genera respuestas personalizadas
 * con códigos de estado HTTP adecuados y mensajes claros.</p>
 */
@RestControllerAdvice
public class GlobalExcepcionHandler {
    /**
     * Mapa para almacenar los detalles de los errores en las respuestas de las excepciones.
     */
    private final Map<String, Object> errorMap = new HashMap<>();

    /**
     * Maneja las excepciones de validación de argumentos no válidos.
     *
     * <p>Cuando un argumento de una solicitud no cumple con las restricciones de validación,
     * se devuelve un mapa de errores que contiene los campos problemáticos y sus mensajes asociados.</p>
     *
     * @param exception la excepción lanzada por argumentos no válidos.
     * @return una respuesta con un mapa de errores y el código de estado HTTP 409 (CONFLICT).
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleInvalidArguments(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return new ResponseEntity<>(errors, HttpStatus.CONFLICT);
    }
    /**
     * Maneja las excepciones de tipo `AlreadyExistsException`.
     *
     * <p>Se devuelve un mensaje indicando que el recurso ya existe.</p>
     *
     * @param exception la excepción lanzada cuando un recurso ya existe.
     * @return una respuesta con un mapa detallando el error y el código de estado HTTP 409 (CONFLICT).
     */
    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> alreadyExistsException(AlreadyExistsException exception){
        errorMap.put("Status", "Error");
        errorMap.put("Message", exception.getMessage());
        errorMap.put("Code", HttpStatus.CONFLICT);

        return new ResponseEntity<>(errorMap, HttpStatus.CONFLICT);
    }
    /**
     * Maneja las excepciones de tipo `UnauthorizedException`.
     *
     * <p>Se devuelve un mensaje indicando que el usuario no está autorizado para realizar la acción.</p>
     *
     * @param exception la excepción lanzada cuando el usuario no está autorizado.
     * @return una respuesta con un mapa detallando el error y el código de estado HTTP 401 (UNAUTHORIZED).
     */
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Map<String, Object>> UnauthorizedException(UnauthorizedException exception){
        errorMap.put("Status", "Error");
        errorMap.put("Message", exception.getMessage());
        errorMap.put("Code", HttpStatus.UNAUTHORIZED);

        return new ResponseEntity<>(errorMap, HttpStatus.UNAUTHORIZED);
    }

}
