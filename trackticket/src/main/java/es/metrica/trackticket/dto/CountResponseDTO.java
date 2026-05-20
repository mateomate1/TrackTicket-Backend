package es.metrica.trackticket.dto;

import jakarta.validation.constraints.PositiveOrZero;

public record CountResponseDTO(
		
		@PositiveOrZero
		Integer amount
		
		) {}
