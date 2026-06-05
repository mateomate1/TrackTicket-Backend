package es.metrica.trackticket.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginResponseDTO(
		
		@NotBlank(message = "Contraseña obligatorio")
		String token,
		
		@NotBlank(message = "Usuario de registro necesario")
		String username
		
		){
	
}
