package es.metrica.trackticket.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Endpoints para login y logout")
public class AuthenticationController {
	
	@PostMapping("/login")
	@Operation(summary = "Iniciar sesión")
	public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO dto) {
		return ResponseEntity.ok(new LoginResponseDTO("token-falso", "usuario_falso"));
	}
	
	@PostMapping("/logout")
	@Operation(summary = "Cerrar sesión")
	@ApiResponse(responseCode = "204", description = "Usuario cerró la sesión correctamente")
	public ResponseEntity<Void> logout(@RequestBody TokenRequestDTO dto) {
		return ResponseEntity.noContent().build();
	}
}
