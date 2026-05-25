package es.metrica.trackticket.dto;

import java.time.LocalDateTime;

public record ErrorDTO(String message, String error, int status, LocalDateTime timestamp) {
}
