package es.metrica.trackticket.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

<<<<<<< HEAD
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
=======
import es.metrica.trackticket.dto.RegisterRequestDTO;
>>>>>>> origin/develop

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "Endpoints para la gestión de la cuenta del usuario")
public class UserController {
	
	@PostMapping("/register")
<<<<<<< HEAD
	@Operation(summary = "Registrar usuario")
	@ApiResponse(responseCode = "201", description = "Usuario registrado correctamente")
	public ResponseEntity<Void> register(@RequestBody RegisterRequestDTO dto) {
		return ResponseEntity.status(HttpStatus.CREATED).build();
=======
	public ResponseEntity<Void> register(@RequestBody RegisterRequestDTO dto) {
	
		return null;
>>>>>>> origin/develop
	}
	
	@PostMapping("/delete")
	@Operation(summary = "Eliminar cuanta de usuario")
	@ApiResponse(responseCode = "204", description = "Cuanta eliminada correctamente")
	public ResponseEntity<Void> deleteAccount(@RequestBody TokenRequestDTO dto) {
		return ResponseEntity.noContent().build();
	}
}
