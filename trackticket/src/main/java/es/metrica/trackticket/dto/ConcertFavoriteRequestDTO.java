package es.metrica.trackticket.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ConcertFavoriteRequestDTO(
		
		@NotBlank(message = "Token de usuario obligatorio")
		String token,
		
		@NotNull(message = "Identificador de concierto obligatorio")
		String idConcierto
		
		) {

}
