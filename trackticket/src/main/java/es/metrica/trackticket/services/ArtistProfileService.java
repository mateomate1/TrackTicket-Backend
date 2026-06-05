package es.metrica.trackticket.services;

import es.metrica.trackticket.dto.ArtistResponseDTO;

public interface ArtistProfileService {
	
	ArtistResponseDTO getArtist (String artistName, String artistGenre);
}
