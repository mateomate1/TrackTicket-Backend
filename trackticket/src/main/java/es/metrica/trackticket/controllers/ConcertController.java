package es.metrica.trackticket.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/concerts")
public class ConcertController {
	

	@GetMapping("/search")
	public ResponseEntity<List<Void>> searchConcerts(@RequestBody SearchDTO dto) {
		
	}
	
	@GetMapping("/{idConcert}")
	public ResponseEntity<Void> getConcertDetails(@PathVariable Long id) {
		
	}
	
	/*
	 * @GetMapping("/search")
	 * public ResponseEntity<List<ConcertResponseDTO>> searchConcerts(
	 * @RequestParam String place,
	 * @RequestParam String artist,
	 * @RequestParam String date) {
	 * 	return null;
	 * }
	 * 
	 * @GetMapping("/{idConcert}")
	 * public ResponseEntity<ConcertResponseDTO> getConcertDetails(@PathVariable("idConcert") String id) {
	 * return null;
	 * }
	 */
}
