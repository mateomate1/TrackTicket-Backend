package es.metrica.trackticket.services;

import java.util.List;
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
		
		SpotifyAlbumsResponse albumResponse = spotifyRestClient.get().uri(uriBuilder -> {
			uriBuilder.path("/artists/{id}/albums").queryParam("market", "ES").queryParam("include_groups", "album")
					.queryParam("limit", 10);
			return uriBuilder.build(artist.getExternalIdArtist());
		}).header("Authorization", "Bearer " + spotifyTokenService.getToken()).retrieve().body(SpotifyAlbumsResponse.class);

		if (albumResponse == null || albumResponse.items().isEmpty()) {
			throw new ResourceNotFoundException("No albums found for that artist");
		}

		List<String> albums = albumResponse.items().stream().map(SpotifyAlbum::name).toList();
		
		artist.setAlbums(albums);
		
		SpotifyPlaylistSearchResponse playlistResponse = spotifyRestClient.get().uri(uriBuilder -> {
			uriBuilder.path("/search").queryParam("q", "This is " + artistName).queryParam("type", "playlist")
					.queryParam("market", "ES").queryParam("limit", 2);
			return uriBuilder.build();
		}).header("Authorization", "Bearer " + spotifyTokenService.getToken()).retrieve().body(SpotifyPlaylistSearchResponse.class);

		if (playlistResponse == null || playlistResponse.playlists == null || playlistResponse.playlists().items().isEmpty()) {
			throw new ResourceNotFoundException("No playlists found for that artist");
		}
		
		String playlistUrl = playlistResponse.playlists().items().get(1).external_urls().spotify();
		
		artist.setPlaylistLink(playlistUrl);

		Optional<Artist> existingArtist = artistRepository.findByExternalIdArtist(artist.getExternalIdArtist());

		if (existingArtist.isPresent()) {
			return existingArtist.get();
		}

		return artistRepository.save(artist);
	}
	
	private record SpotifyAlbumsResponse(List<SpotifyAlbum> items) {
	}

	private record SpotifyAlbum(String name) {
	}
	
	private record SpotifyPlaylistSearchResponse(SpotifyPlaylistItems playlists) {
	}

	private record SpotifyPlaylistItems(List<SpotifyPlaylist> items) {
	}

	private record SpotifyPlaylist(SpotifyExternalUrls external_urls) {
	}
	
	private record SpotifyExternalUrls(String spotify) {
	}
}
