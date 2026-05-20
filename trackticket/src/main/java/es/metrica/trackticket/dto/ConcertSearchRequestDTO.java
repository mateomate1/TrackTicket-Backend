package es.metrica.trackticket.dto;

import java.time.LocalDate;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ConcertSearchRequestDTO(
		
		@NotNull(message = "La fecha es oligatoria")
		LocalDate date,
		
		@Positive(message = "Identificador de usuario debe ser positivo")
		Long artista,
		
		@Valid
		VenueDTO venue
		
		) {}
