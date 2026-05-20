package es.metrica.trackticket.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequestDTO( 
		
		@NotBlank(message = "El nombre es obligatorio")
		String name, 
		
		@NotBlank(message = "El email es obligatorio")
		@Email(message = "El email debe tener formato email") 
		String email,
		
		@NotBlank(message = "La contraseña es obligatoria") 
		@Size(min=8, message = "Debe contener al un mínimo de 8 carácteres") 
		String password ) {
	
	/**
	 * Añadir a los Controllers -> @Valid anotación
	 */

}
