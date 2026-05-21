package es.metrica.trackticket.dto;

import java.time.LocalDate;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ConcertSearchRequestDTO(
		
		@NotNull(message = "La fecha es oligatoria de inicio")
		LocalDate startDate,
		
		LocalDate finalDay,
		
		@Positive(message = "Identificador de usuario debe ser positivo")
		Long artista,
		
		String location
		
		) {}
