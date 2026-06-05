package es.metrica.trackticket.services;

import java.util.List;

import es.metrica.trackticket.dto.ArtistResponseDTO;
import es.metrica.trackticket.dto.FavouriteArtistRequestDTO;
import es.metrica.trackticket.dto.TokenRequestDTO;

public interface FavouriteArtistService {
	
	List<ArtistResponseDTO> getFavouriteArtists(TokenRequestDTO dto);
	
	void addFavouriteArtist(FavouriteArtistRequestDTO dto);
	
	void deleteFavouriteArtist(FavouriteArtistRequestDTO dto);
	
	boolean isFavouriteArtist(FavouriteArtistRequestDTO dto);
}
