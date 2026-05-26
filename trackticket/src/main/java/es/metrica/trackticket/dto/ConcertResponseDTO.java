package es.metrica.trackticket.dto;

import java.time.LocalDate;

public record ConcertResponseDTO (
		
		String idTicketMaster,
		String name, 
		LocalDate date, 
		String link,
		String artistName,
		String artistGenre,
		VenueDTO venue
		
		) {}
