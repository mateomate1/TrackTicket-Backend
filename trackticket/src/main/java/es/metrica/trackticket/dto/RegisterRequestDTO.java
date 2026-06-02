package es.metrica.trackticket.dto;

import jakarta.validation.constraints.NotBlank;


public record RegisterRequestDTO(


        @NotBlank(message = "El nombre es obligatorio")
        String name,

        @NotBlank(message = "El email es obligatorio")
        String email,

        @NotBlank(message = "La contraseña es obligatoria")
        String password ) {

}