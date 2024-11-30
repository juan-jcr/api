package com.rest.api.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TokenResponseDTO {
    /**
     * la respuesta del token después de iniciar sesion con exito
     */
    private String token;
}
