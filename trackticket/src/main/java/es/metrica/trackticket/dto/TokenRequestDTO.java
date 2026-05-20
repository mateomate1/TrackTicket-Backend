package es.metrica.trackticket.dto;

import jakarta.validation.constraints.NotNull;

public record TokenRequestDTO(
		
		@NotNull(message = "Token de usuario obligatorio")
		String token
		
		) {

}
