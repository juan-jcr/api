package com.rest.api.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 *  Objeto que contiene la información del usuario
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    /**
     * el nombre de usuario no puede estar vacío ni contener espacios
     */
    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    @Pattern(regexp = "^[^\\s]+$", message = "El nombre de usuario no debe contener espacios")
    private String username;
    /**
     * No puede estar vacío y debe tener como mínimo 6 caracteres
     */
    @NotEmpty(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;
}
