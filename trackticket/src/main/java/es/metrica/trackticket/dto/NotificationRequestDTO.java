package es.metrica.trackticket.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NotificationRequestDTO(
		
		@NotBlank(message = "Token de usuario obligatorio")
		String token, 
		
		@NotNull(message = "Identificador de notificación obligatorio")
		Long idNotification
		
		) {}
