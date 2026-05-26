package es.metrica.trackticket.dto;

import java.util.List;

public record ArtistResponseDTO (
		
		String idArtist, 
		String name,
		String linkList,
		String linkImage,
		List<String> albums
		) {}
