package com.rest.api.presentation.controller;

import com.rest.api.presentation.dto.TokenResponseDTO;
import com.rest.api.presentation.dto.UserDTO;
import com.rest.api.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador de autenticación que gestiona el registro y el inicio de sesión de usuarios.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    /**
     * Servicio de autenticación que contiene la lógica de negocio para el registro y el inicio de sesión.
     */
    private final AuthService authService;
    /**
     * Endpoint para el inicio de sesión de usuarios.
     *
     * <p>Recibe las credenciales del usuario y devuelve un token JWT si las credenciales son válidas.</p>
     *
     * @param userDTO objeto que contiene las credenciales del usuario (nombre de usuario y contraseña).
     * @return una respuesta con un token JWT en el cuerpo de la respuesta y un código de estado HTTP 200.
     */
    @PostMapping("/log-in")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody @Valid UserDTO userDTO){
        return new ResponseEntity<>(authService.login(userDTO), HttpStatus.OK);
    }

    /**
     * Endpoint para el registro de nuevos usuarios.
     *
     * <p>Permite a los usuarios registrarse proporcionando un nombre de usuario y una contraseña.
     * Si el registro es exitoso, devuelve un mensaje de confirmación.</p>
     *
     * @param userDTO objeto que contiene los datos del usuario (nombre de usuario y contraseña).
     * @return una respuesta con un mensaje de confirmación en el cuerpo de la respuesta y un código de estado HTTP 200.
     */
    @PostMapping("/sign-up")
    public ResponseEntity<String> register(@RequestBody @Valid  UserDTO userDTO){
       return new ResponseEntity<>(authService.register(userDTO), HttpStatus.OK);
    }
}
