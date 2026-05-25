package es.metrica.trackticket.dto;

import java.time.LocalDate;

public record ConcertDetailsResponseDTO(
		
		String nombre,
		LocalDate date,
		VenueDTO venue,
		ArtistResponseDTO artista,
		String linkArtista,
		String linkCompra
		
		) 
		{}
