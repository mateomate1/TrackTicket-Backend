package es.metrica.trackticket.services;

import es.metrica.trackticket.models.Artist;

public interface FindAndSaveArtistService {

	Artist getArtistFromSpotifyAndSave(String spotifyId, String artistGenre);

	Artist getArtistByNameFromSpotifyAndSave(String artistName, String artistGenre);

}
