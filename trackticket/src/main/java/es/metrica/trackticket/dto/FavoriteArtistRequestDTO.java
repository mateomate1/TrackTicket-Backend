package es.metrica.trackticket.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FavoriteArtistRequestDTO(
		
		@NotBlank(message = "Token de usuario obligatorio")
		String token,
		
		@NotNull(message = "Identificador del artista obligatorio")
		Integer idArtist
		
		) {

}
