package es.metrica.trackticket.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.metrica.trackticket.dto.ConcertResponseDTO;
import es.metrica.trackticket.dto.ConcertSearchRequestDTO;
import es.metrica.trackticket.dto.VenueDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/v1/concerts")
@Tag(name = "Concerts", description = "Endpoints para búsqueda de conciertos")
public class ConcertController {

	@PostMapping("/search")
	@Operation(summary = "Buscar conciertos", description = "Buscar por fecha (obligatorio) y artista/región")
	public ResponseEntity<List<ConcertResponseDTO>> searchConcertsList(@RequestBody ConcertSearchRequestDTO dto) {
		return ResponseEntity.ok(List.of());
	}

	@PostMapping("/details")
	@Operation(summary = "Ver detalles de un concierto")
	public ResponseEntity<ConcertResponseDTO> getConcertDetails(@RequestParam String idConcertTicketmaster) {
		return ResponseEntity.ok(new ConcertResponseDTO("idConcert", "name", LocalDateTime.now(), "link",
				new VenueDTO("name", 0.0, 0.0, "address", "state", "country")));
	}
}
