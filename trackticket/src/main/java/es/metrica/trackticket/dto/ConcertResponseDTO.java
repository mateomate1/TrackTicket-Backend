package es.metrica.trackticket.dto;

import java.time.LocalDateTime;

public record ConcertResponseDTO (
		
		String idTicketMaster,
		String name, 
		LocalDateTime date, 
		String link,
		VenueDTO venue
		
		) {}
