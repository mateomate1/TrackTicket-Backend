package es.metrica.trackticket.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.metrica.trackticket.dto.ArtistResponseDTO;
import es.metrica.trackticket.dto.FavouriteArtistRequestDTO;
import es.metrica.trackticket.dto.TokenRequestDTO;
import es.metrica.trackticket.services.FavouriteArtistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/v1/favourites/artists")
@Tag(name = "Favourite artists", description = "Endpoints para gestión de artistas favoritos")
public class FavouriteArtistController {

	private final FavouriteArtistService favouriteArtistService;

	public FavouriteArtistController(FavouriteArtistService favouriteArtistService) {
		this.favouriteArtistService = favouriteArtistService;
	}

	@PostMapping("/list")
	@Operation(summary = "Listar artistas favoritos")
	public ResponseEntity<List<ArtistResponseDTO>> getFavArtistList(@RequestBody TokenRequestDTO dto) {
		return ResponseEntity.ok(favouriteArtistService.getFavouriteArtists(dto));
	}

	@PostMapping("/add")
	@Operation(summary = "Añadir artista a favoritos")
	@ApiResponse(responseCode = "201", description = "Artista añadido correctamente")
	public ResponseEntity<Void> addFavArtist(@RequestBody FavouriteArtistRequestDTO dto) {
		favouriteArtistService.addFavouriteArtist(dto);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PostMapping("/remove")
	@Operation(summary = "Eliminar artista de favoritos")
	@ApiResponse(responseCode = "204", description = "Artista eliminado correctamente")
	public ResponseEntity<Void> removeFavArtist(@RequestBody FavouriteArtistRequestDTO dto) {
		favouriteArtistService.deleteFavouriteArtist(dto);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/is-fav")
	@Operation(summary = "Verificar si un artista está guardado como favoritos o no")
	public ResponseEntity<Boolean> isFavArtist(@RequestBody FavouriteArtistRequestDTO dto) {
		return ResponseEntity.ok(favouriteArtistService.isFavouriteArtist(dto));
	}

}
