package es.metrica.trackticket.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.metrica.trackticket.dto.RegisterRequestDTO;
import es.metrica.trackticket.dto.TokenRequestDTO;
import es.metrica.trackticket.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/v1/users")
@Tag(name = "Users", description = "Endpoints para la gestión de la cuenta del usuario")
public class UserController {
	
	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	@PostMapping("/register")
	@Operation(summary = "Registrar usuario")
	@ApiResponse(responseCode = "201", description = "Usuario registrado correctamente")
	public ResponseEntity<Void> register(@Valid @RequestBody RegisterRequestDTO dto) {
		userService.register(dto);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@PostMapping("/delete")
	@Operation(summary = "Eliminar cuanta de usuario")
	@ApiResponse(responseCode = "204", description = "Cuanta eliminada correctamente")
	public ResponseEntity<Void> deleteAccount(@RequestBody TokenRequestDTO dto) {
		return ResponseEntity.noContent().build();
	}
}
