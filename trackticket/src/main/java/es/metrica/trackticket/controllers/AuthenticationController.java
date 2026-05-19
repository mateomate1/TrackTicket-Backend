package es.metrica.trackticket.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	
	@PostMapping("/login")
	public ResponseEntity<Void> login(@RequestBody LoginDTO dto) {
		return null;
	}
	
	/*
	 * @PostMapping("/register")
	 * public ResponseEntity<Void> register(@RequestBody RegisterRequestDTO dto) {
	 * return null;
	 * }
	 * @PostMapping("/login")
	 * public ResponseEntity<Void> login(@RequestBody LoginDTO dto) {
	 * return null;
	 * }
	 * @PostMapping("/logout")
	 * public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token) {
	 * return null;
	 * }
	 */
}
