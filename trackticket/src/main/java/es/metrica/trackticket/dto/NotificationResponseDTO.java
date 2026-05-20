package es.metrica.trackticket.dto;

import java.time.LocalDateTime;

public record NotificationResponseDTO(
		
		String message, 
		String type,
		boolean read,
		LocalDateTime launch
		
		) {}
