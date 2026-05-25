package es.metrica.trackticket.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;

import es.metrica.trackticket.dto.ErrorDTO;

@RestControllerAdvice
public class GlobalHandlerException {

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorDTO> handleIllegalArgumentException(IllegalArgumentException e) {
		ErrorDTO response = new ErrorDTO(e.getMessage(), "Illegal argument", HttpStatus.BAD_REQUEST.value(),
				LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(response);
	}

	@ExceptionHandler(HttpServerErrorException.class)
	public ResponseEntity<ErrorDTO> handleExternalServerError(HttpServerErrorException e) {
		ErrorDTO response = new ErrorDTO(e.getMessage(), "The external server is not available at the moment.",
				HttpStatus.BAD_GATEWAY.value(), LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.BAD_GATEWAY.value()).body(response);
	}

}
