package com.rest.api.presentation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador para realzar prueba de acceso al endpoint con token.
 */
@RestController
@RequestMapping("/api/v1")
public class HelloController {

    @GetMapping("/hello")
    public String helloWord(){
        return "Hola mundo";
    }
}
