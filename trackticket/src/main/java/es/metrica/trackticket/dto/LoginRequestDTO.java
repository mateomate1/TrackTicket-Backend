package es.metrica.trackticket.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequestDTO(
		
		@NotBlank(message = "Nombre obligatorio")
		String user, 
		
		@NotBlank(message = "Contraseña obligatoria")
		@Size(min = 8)
		String password ) {

}
