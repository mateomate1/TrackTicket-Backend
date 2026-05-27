package es.metrica.trackticket.controllers;

import java.security.KeyPair;
import java.util.Base64;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.metrica.trackticket.dto.LoginRequestDTO;
import es.metrica.trackticket.dto.TokenRequestDTO;
import es.metrica.trackticket.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/v1/auth")
@Tag(name = "Authentication", description = "Endpoints para login y logout")
public class AuthenticationController {
	
	private final KeyPair keyPair;
	private final UserService userService;
	
	public AuthenticationController(KeyPair keyPair, UserService userService) {
		this.keyPair = keyPair;
		this.userService = userService;
		
	}
	
	@PostMapping("/public-key")
	public ResponseEntity<String> publicKey() {
		return ResponseEntity.ok(
					Base64.getEncoder().encodeToString(
							
							keyPair.getPublic().getEncoded()
					)
					
				);
	}

	
	@PostMapping("/login")
	@Operation(summary = "Iniciar sesión")
	public ResponseEntity<String> login(@Valid @RequestBody LoginRequestDTO dto) {
		return ResponseEntity.ok(userService.login(dto));
	}
	
	@PostMapping("/logout")
	@Operation(summary = "Cerrar sesión")
	@ApiResponse(responseCode = "204", description = "Usuario cerró la sesión correctamente")
	public ResponseEntity<Void> logout(@Valid @RequestBody TokenRequestDTO dto) {
		userService.logOut(dto);
		
		return ResponseEntity.noContent().build();
	}
}
