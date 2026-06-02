package es.metrica.trackticket.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.metrica.trackticket.dto.ConcertFavoriteRequestDTO;
import es.metrica.trackticket.dto.ConcertResponseDTO;
import es.metrica.trackticket.dto.TokenRequestDTO;
import es.metrica.trackticket.services.FavouriteConcertService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/v1/favourites/concerts")
@Tag(name = "Favourite concerts", description = "Endpoints para gestión de conciertos favoritos")
public class FavouriteConcertController {

	private final FavouriteConcertService favouriteConcertService;

	public FavouriteConcertController(FavouriteConcertService favouriteConcertService) {
		this.favouriteConcertService = favouriteConcertService;
	}

	@PostMapping("/list")
	@Operation(summary = "Listar conciertos favoritos")
	public ResponseEntity<List<ConcertResponseDTO>> getFavConcertList(@RequestBody TokenRequestDTO dto) {

		return ResponseEntity.ok(favouriteConcertService.getFavConcertList(dto));
	}

	@PostMapping("/add")
	@Operation(summary = "Añadir concierto a favoritos")
	@ApiResponse(responseCode = "201", description = "Concierto añadido correctamente")
	public ResponseEntity<Void> addFavConcert(@RequestBody ConcertFavoriteRequestDTO dto) {

		favouriteConcertService.addFavConcert(dto);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PostMapping("/remove")
	@Operation(summary = "Eliminar concierto de favoritos")
	@ApiResponse(responseCode = "204", description = "Concierto eliminado correctamente")
	public ResponseEntity<Void> removeFavConcert(@RequestBody ConcertFavoriteRequestDTO dto) {
		favouriteConcertService.removeFavConcert(dto);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/is-fav")
	@Operation(summary = "Verificar si un concierto está guardado como favorito o no")
	public ResponseEntity<Boolean> isFavConcert(@RequestBody ConcertFavoriteRequestDTO dto) {
		return ResponseEntity.ok(true);
	}

}