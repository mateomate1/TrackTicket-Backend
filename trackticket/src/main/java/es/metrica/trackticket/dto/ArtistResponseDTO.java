package es.metrica.trackticket.dto;

import java.util.List;

public record ArtistResponseDTO (
		
		String idArtist, 
		String name,
		String spotifyProfileLink,
		String linkList,
		String linkImage,
		String genre,
		List<String> albums
		) {}
