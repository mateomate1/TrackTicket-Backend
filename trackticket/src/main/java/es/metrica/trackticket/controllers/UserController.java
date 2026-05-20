package es.metrica.trackticket.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.metrica.trackticket.dto.RegisterRequestDTO;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@PostMapping("/register")
	public ResponseEntity<Void> register(@RequestBody RegisterRequestDTO dto) {
	
		return null;
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<Void> delete(@RequestBody DeleteDTO dto) {
		
	}
	
	@GetMapping("/favartists")
	public ResponseEntity<List<Void>> favArtistList(@RequestHeader("user") String user) {
		//TODO: Revisar cómo identificar usuario
	}
	
	@PostMapping("/favartists/{idArtist}")
	public ResponseEntity<Void> addFavArtist(@RequestHeader("user") String user, @PathVariable Long id, @RequestBody FavArtistDTO) {
		//TODO: Revisar cómo identificar usuario
	}
	
	@DeleteMapping("/favartists/{idArtist}")
	public ResponseEntity<Void> delFavArtist(@RequestHeader("user") String user, @PathVariable Long id) {
		//TODO: Revisar cómo identificar usuario
	}
	
	@GetMapping("/favconcerts")
	public ResponseEntity<List<Void>> favConcertList(@RequestHeader("user") String user) {
		//TODO: Revisar cómo identificar usuario
	}
	
	@PostMapping("/favconcerts/{idConcert}")
	public ResponseEntity<Void> addFavConcert(@RequestHeader("user") String user, @PathVariable Long id, @RequestBody FavConcertDTO) {
		//TODO: Revisar cómo identificar usuario
	}
	
	@DeleteMapping("/favconcerts/{idConcert}")
	public ResponseEntity<Void> delFavConcert(@RequestHeader("user") String user, @PathVariable Long id) {
		//TODO: Revisar cómo identificar usuario
	}
	
	/*
	 * @DeleteMapping("/user")
	 * public ResponseEntity<Void> deleteAccount(@RequestHeader("Authorization") String token) {
	 * return null;
	 * }
	 * 
	 * @GetMapping("/user/favartists")
	 * public ResponseEntity<List<ArtistResponseDTO>> getFavArtistList(@RequestHeader("Authorization") String token) {
	 * return null;
	 * }
	 * 
	 * @PostMapping("user/favartists/{idArtist}")
	 * public ResponseEntity<Void> addFavArtist(@RequestHeader("Authorization") String token, @PathVariable("idArtist") String id) {
	 * return null;
	 * }
	 * 
	 * @DeleteMapping("user/favartists/{idArtist}")
	 * public ResponseEntity<Void> delFavArtist(@RequestHeader("Authorization") String token, @PathVariable("idArtist") String id) {
	 * return null;
	 * }
	 * 
	 * @GetMapping("/user/favconcerts")
	 * public ResponseEntity<List<Void>> favConcertList(@RequestHeader("Authorization") String token) {
	 * return null
	 * }
	 * 
	 * @PostMapping("user/favconcerts/{idConcert}")
	 * public ResponseEntity<Void> addFavConcert(@RequestHeader("Authorization") String token, @PathVariable("idConcert") String id) {
	 * return null;
	 * }
	 * @DeleteMapping("/favconcerts/{idConcert}")
	 * public ResponseEntity<Void> delFavConcert(@RequestHeader("Authorization") String token, @PathVariable("idConcert") String id) {
	 * return null;
	 * }
	 */
	
}
