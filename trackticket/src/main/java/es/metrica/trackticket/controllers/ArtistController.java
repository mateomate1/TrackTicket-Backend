package es.metrica.trackticket.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/artists")
public class ArtistController {
	
	@GetMapping("/{idArtist}")
	public ResponseEntity<Void> getArtistDetails(@RequestParam("idArtist") String id) {
		return null;
	}
	
	/*
	 * @GetMapping("/{idArtist}")
	 * public ResponseEntity<ArtistResponseDTO> getArtistDetails(@PathVariable("idArtist") String id) {
	 * return null;
	 * }
	 */
	
}
