package es.metrica.trackticket.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/artists")
@Tag(name = "Artists", description = "Endpoints para artistas")
public class ArtistController {
	
	@PostMapping("/profile")
	@Operation(summary = "Ver perfil de un artista")
	public ResponseEntity<ArtistResponseDTO> getArtistProfile(@RequestParam Long idArtist) {
		return ResponseEntity.ok(null);
	}
}
