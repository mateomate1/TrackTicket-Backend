package es.metrica.trackticket.services;

import es.metrica.trackticket.dto.ConcertFavoriteRequestDTO;

public interface FavouriteConcertService {
	void addFavConcert(ConcertFavoriteRequestDTO dto);
}
