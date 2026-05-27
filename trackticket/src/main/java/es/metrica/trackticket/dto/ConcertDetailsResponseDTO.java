package es.metrica.trackticket.dto;

import java.time.LocalDate;


public record ConcertDetailsResponseDTO(
		
		String idTicketMaster,
		String concertName,
		LocalDate concertDate,
		String buyLink,
		String artistName,
		String artistLink,
		VenueDTO venue
		
		) 
		{}
