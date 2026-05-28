package es.metrica.trackticket.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import es.metrica.trackticket.dto.mapper.ArtistMapper;
import es.metrica.trackticket.dto.mapper.ArtistMapper.SpotifyArtistSearchResponse;
import es.metrica.trackticket.dto.mapper.ArtistMapper.SpotifyGetArtistResponse;
import es.metrica.trackticket.exception.ResourceNotFoundException;
import es.metrica.trackticket.models.Artist;
import es.metrica.trackticket.repositories.ArtistRepository;

@Service
public class FindAndSaveArtistServiceImpl implements FindAndSaveArtistService {

	private ArtistRepository artistRepository;
	private RestClient spotifyRestClient;
	private SpotifyTokenService spotifyTokenService;

	public FindAndSaveArtistServiceImpl(RestClient.Builder restClientBuilder,
			@Value("${spotify.api.url}") String apiUrl, SpotifyTokenService spotifyTokenService,
			ArtistRepository artistRepository) {
		this.spotifyRestClient = restClientBuilder.clone().baseUrl(apiUrl).build();
		this.spotifyTokenService = spotifyTokenService;
		this.artistRepository = artistRepository;
	}

	@Override
	public Artist getArtistFromSpotifyAndSave(String spotifyId, String artistGenre) {

		Optional<Artist> existingArtist = artistRepository.findByExternalIdArtist(spotifyId);

		if (existingArtist.isPresent()) {
			return existingArtist.get();
		}

		SpotifyGetArtistResponse response = spotifyRestClient.get().uri("/artists/{id}", spotifyId)
				.header("Authorization", "Bearer " + spotifyTokenService.getToken()).retrieve()
				.body(SpotifyGetArtistResponse.class);

		if (response == null) {
			throw new ResourceNotFoundException("Artist not found");
		}

		Artist artist = ArtistMapper.mapToArtistWithId(response, spotifyId, artistGenre);

		return artistRepository.save(artist);
	}

	@Override
	public Artist getArtistByNameFromSpotifyAndSave(String artistName, String artistGenre) {

		SpotifyArtistSearchResponse response = spotifyRestClient.get().uri(uriBuilder -> {
			uriBuilder.path("/search").queryParam("q", artistName).queryParam("type", "artist")
					.queryParam("market", "ES").queryParam("limit", 1);
			return uriBuilder.build();
		}).header("Authorization", "Bearer " + spotifyTokenService.getToken()).retrieve()
				.body(SpotifyArtistSearchResponse.class);

		if (response == null || response.artists().items().isEmpty()) {
			throw new ResourceNotFoundException("No se encuentran resultados para ese artista.");
		}

		Artist artist = ArtistMapper.mapToArtistWithName(artistName, artistGenre, response);

		Optional<Artist> existingArtist = artistRepository.findByExternalIdArtist(artist.getExternalIdArtist());

		if (existingArtist.isPresent()) {
			return existingArtist.get();
		}

		return artistRepository.save(artist);
	}
}
