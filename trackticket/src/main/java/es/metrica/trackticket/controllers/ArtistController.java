package es.metrica.trackticket.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.metrica.trackticket.dto.ArtistResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/v1/artists")
@Tag(name = "Artists", description = "Endpoints para artistas")
public class ArtistController {

	@PostMapping("/profile")
	@Operation(summary = "Ver perfil de un artista")
	public ResponseEntity<ArtistResponseDTO> getArtistProfile(@RequestParam String artistName) {
		return ResponseEntity.ok(
				new ArtistResponseDTO("idArtist", "nameArtist", "linkSpotifyList", "linkImage", "genre", List.of()));
	}
}
