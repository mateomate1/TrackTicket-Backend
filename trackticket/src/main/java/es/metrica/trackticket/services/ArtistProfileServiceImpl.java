package es.metrica.trackticket.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import es.metrica.trackticket.dto.ArtistResponseDTO;
import es.metrica.trackticket.dto.mapper.ArtistMapper;
import es.metrica.trackticket.dto.mapper.ArtistMapper.SpotifyArtistSearchResponse;
import es.metrica.trackticket.exception.ResourceNotFoundException;

@Service
public class ArtistProfileServiceImpl implements ArtistProfileService {

	private RestClient restClientArtistSearch;
	private RestClient restClientToken;
	private String token;
	private LocalDateTime tokenExpiration;
	private String clientId;
	private String clientSecret;

	public ArtistProfileServiceImpl(RestClient.Builder restClientBuilder, @Value("${spotify.api.url}") String apiUrl,
			@Value("${spotify.token.url}") String tokenUrl, @Value("${spotify.client.id}") String clientId,
			@Value("${spotify.client.secret}") String clientSecret) {
		this.restClientArtistSearch = restClientBuilder.clone().baseUrl(apiUrl).build();
		this.restClientToken = restClientBuilder.clone().baseUrl(tokenUrl).build();
		this.tokenExpiration = LocalDateTime.now().minusSeconds(1);
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.token = "";
	}

	private String getToken() {

		if (LocalDateTime.now().isAfter(this.tokenExpiration)) {
			String credentials = this.clientId + ":" + this.clientSecret;
			credentials = Base64.getEncoder().encodeToString(credentials.getBytes());

			SpotifyTokenResponse response = restClientToken.post().contentType(MediaType.APPLICATION_FORM_URLENCODED)
					.header("Authorization", "Basic " + credentials).body("grant_type=client_credentials").retrieve()
					.body(SpotifyTokenResponse.class);

			this.token = response.access_token();
			this.tokenExpiration = LocalDateTime.now().plusSeconds(response.expires_in());
		}

		return this.token;
	}

	@Override
	public ArtistResponseDTO getArtist(String artistName, String artistGenre) {

		if (artistName == null || artistName.isBlank()) {
			throw new IllegalArgumentException("El nombre del artista no puede estar vacío.");
		}

		SpotifyArtistSearchResponse response = restClientArtistSearch.get().uri(uriBuilder -> {
			uriBuilder.path("/search").queryParam("q", artistName).queryParam("type", "artist")
					.queryParam("market", "ES").queryParam("limit", 1);
			return uriBuilder.build();
		}).header("Authorization", "Bearer " + this.getToken()).retrieve().body(SpotifyArtistSearchResponse.class);

		if (response == null || response.artists().items().isEmpty()) {
			throw new ResourceNotFoundException("No se encuentran resultados para ese artista.");
		}

		List<String> albums = this.getAlbums(response.artists().items().getFirst().id());
		String playlistUrl = this.getPlaylist(artistName);

		return ArtistMapper.mapToArtistResponseDTO(artistName, artistGenre, response, albums, playlistUrl);
	}

	private List<String> getAlbums(String artistId) {

		SpotifyAlbumsResponse response = restClientArtistSearch.get().uri(uriBuilder -> {
			uriBuilder.path("/artists/{id}/albums").queryParam("market", "ES").queryParam("include_groups", "album")
					.queryParam("limit", 10);
			return uriBuilder.build(artistId);
		}).header("Authorization", "Bearer " + this.getToken()).retrieve().body(SpotifyAlbumsResponse.class);

		return response.items().stream().map(SpotifyAlbum::name).toList();
	}

	private String getPlaylist(String artistName) {

		SpotifyPlaylistSearchResponse response = restClientArtistSearch.get().uri(uriBuilder -> {
			uriBuilder.path("/search").queryParam("q", "This is " + artistName).queryParam("type", "playlist")
					.queryParam("market", "ES").queryParam("limit", 2);
			return uriBuilder.build();
		}).header("Authorization", "Bearer " + this.getToken()).retrieve().body(SpotifyPlaylistSearchResponse.class);

		if (response == null || response.playlists == null || response.playlists().items().isEmpty()) {
			throw new ResourceNotFoundException("No playlists found for that artist");
		}

		for (SpotifyPlaylist playlist : response.playlists().items()) {
			if (playlist != null && playlist.external_urls() != null) {
				return playlist.external_urls().spotify();
			}
		}

		throw new ResourceNotFoundException("No playlists found for that artist");
	}

	private record SpotifyTokenResponse(String access_token, int expires_in) {
	}

	private record SpotifyExternalUrls(String spotify) {
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
}
