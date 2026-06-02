package es.metrica.trackticket.services;

import java.util.List;

import es.metrica.trackticket.dto.ConcertFavoriteRequestDTO;
import es.metrica.trackticket.dto.ConcertResponseDTO;
import es.metrica.trackticket.dto.TokenRequestDTO;

public interface FavouriteConcertService {
	void addFavConcert(ConcertFavoriteRequestDTO dto);
	void removeFavConcert(ConcertFavoriteRequestDTO dto);
	List<ConcertResponseDTO> getFavConcertList(TokenRequestDTO dto);
}
