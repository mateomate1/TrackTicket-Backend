package es.metrica.trackticket.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;

public record ConcertSearchRequestDTO(
		
		@NotNull(message = "La fecha es oligatoria de inicio")
		LocalDateTime startDate,
		
		LocalDateTime finalDay,
		
		String artista,
		
		String location
		
		) {}
