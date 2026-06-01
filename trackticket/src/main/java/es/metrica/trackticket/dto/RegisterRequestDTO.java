package es.metrica.trackticket.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequestDTO( 
		
		@NotBlank(message = "El nombre es obligatorio")
		String name, 
		
		@NotBlank(message = "El email es obligatorio")
		
		String email,
		
		@NotBlank(message = "La contraseña es obligatoria") 
		
		String password ) {
	
	/**
	 * Añadir a los Controllers -> @Valid anotación
	 */

}
