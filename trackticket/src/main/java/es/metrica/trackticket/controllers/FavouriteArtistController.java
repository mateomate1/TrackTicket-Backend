package es.metrica.trackticket.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.metrica.trackticket.dto.ArtistResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("favourites/artists")
@Tag(name = "Favourite artists", description = "Endpoints para gestión de artistas favoritos")
public class FavouriteArtistController {
	
	@PostMapping("/list")
	@Operation(summary = "Listar artistas favoritos")
	public ResponseEntity<List<ArtistResponseDTO>> getFavArtistList(@RequestBody TokenRequestDTO dto) {
		return ResponseEntity.ok(List.of());
	}
	
	@PostMapping("/add")
	@Operation(summary = "Añadir artista a favoritos")
	@ApiResponse(responseCode = "201", description = "Artista añadido correctamente")
	public ResponseEntity<Void> addFavArtist(@RequestBody FavouriteArtistRequestDTO dto) {
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@PostMapping("/remove")
	@Operation(summary = "Eliminar artista de favoritos")
	@ApiResponse(responseCode = "204", description = "Artista eliminado correctamente")
	public ResponseEntity<Void> addFavArtist(@RequestBody FavouriteArtistRequestDTO dto) {
		return ResponseEntity.noContent().build();
	}
	
}
