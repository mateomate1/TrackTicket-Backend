package es.metrica.trackticket.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FavouriteArtistRequestDTO(@NotBlank(message = "Token de usuario obligatorio") String token,
		@NotNull(message = "Identificador del artista obligatorio") String idArtist,
		@NotNull(message = "Género musical del artista obligatorio") String artistGenre) {
}
