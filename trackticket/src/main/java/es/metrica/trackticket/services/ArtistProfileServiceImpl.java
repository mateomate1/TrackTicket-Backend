package es.metrica.trackticket.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import es.metrica.trackticket.dto.ArtistResponseDTO;
import es.metrica.trackticket.dto.mapper.ArtistMapper;
import es.metrica.trackticket.exception.ResourceNotFoundException;

public class ArtistProfileServiceImpl implements ArtistProfileService {

	private RestClient restClientArtistSearch;
	private RestClient restClientToken;
	private String spotifyApiUrl;
	private String spotifyTokenUrl;
	private String token;
	private LocalDateTime tokenExpiration;
	private String clientId;
	private String clientSecret;

	public ArtistProfileServiceImpl(RestClient.Builder restClientBuilder, @Value("${spotify.api.url}") String apiUrl,
			@Value("${spotify.token.url}") String tokenUrl, @Value("${spotify.client.id}") String clientId,
			@Value("${spotify.client.secret}") String clientSecret) {
		this.restClientArtistSearch = restClientBuilder.baseUrl(apiUrl).build();
		this.restClientToken = restClientBuilder.baseUrl(tokenUrl).build();
		this.spotifyApiUrl = apiUrl;
		this.spotifyTokenUrl = tokenUrl;
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
					.header("Authorization", "Basic" + credentials).body("grant_type=client_credentials").retrieve()
					.body(SpotifyTokenResponse.class);

			this.token = response.access_token();
			this.tokenExpiration = LocalDateTime.now().plusSeconds(response.expires_in());
		}

		return this.token;
	}

	@Override
	public ArtistResponseDTO getArtist(String artistName) {

		if (artistName != null && !artistName.isBlank()) {
			throw new IllegalArgumentException("El nombre del artista no puede estar vacío.");
		}

		SpotifyArtistSearchResponse response = restClientArtistSearch.get().uri(uriBuilder -> {
			uriBuilder.path("/search?").queryParam("q", artistName).queryParam("type", "artist")
					.queryParam("market", "ES").queryParam("limit", 1);
			return uriBuilder.build();
		}).header("Authorization", "Bearer " + this.getToken()).retrieve().body(SpotifyArtistSearchResponse.class);

		if (response == null || response.artists().items().isEmpty()) {
			throw new ResourceNotFoundException("No se encuentran resultados para ese artista.");
		}

		List<String> topTracks = this.getTopTracks(response.artists().items().getFirst().id());
		String playlistUrl = this.getPlaylist(artistName);

		return ArtistMapper.mapToArtistResponseDTO(response, topTracks, playlistUrl);
	}

	private List<String> getTopTracks(String artistId) {

		SpotifyTopTracksResponse response = restClientArtistSearch.get().uri(uriBuilder -> {
			uriBuilder.path("/artists/{id}/top-tracks").queryParam("market", "ES");
			return uriBuilder.build(artistId);
		}).header("Authorization", "Bearer " + this.getToken()).retrieve().body(SpotifyTopTracksResponse.class);

		List<String> topTracksNames = new ArrayList<>();

		for (SpotifyTrack track : response.tracks()) {
			topTracksNames.add(track.name());
		}

		return topTracksNames;
	}

	private String getPlaylist(String artistName) {

		SpotifyPlaylistSearchResponse response = restClientArtistSearch.get().uri(uriBuilder -> {
			uriBuilder.path("/search?").queryParam("q", "This is " + artistName).queryParam("type", "playlist")
					.queryParam("market", "ES").queryParam("limit", 2);
			return uriBuilder.build();
		}).header("Authorization", "Bearer " + this.getToken()).retrieve().body(SpotifyPlaylistSearchResponse.class);

		if (response == null || response.playlists == null) {
			throw new ResourceNotFoundException("No playlists found for that artist");
		}

		if (response.playlists().items().getFirst() == null) {
			try {
			return response.playlists().items().get(1).external_urls().spotify();
			} catch(NullPointerException e) {
				throw new ResourceNotFoundException("No playlists found for that artist");
			}
		}

		return null;
	}

	public void mapToArtistResponseDTO() {

	}

	private record SpotifyTokenResponse(String access_token, int expires_in) {
	}

	public record SpotifyArtistSearchResponse(SpotifyArtistItems artists) {
	}

	private record SpotifyArtistItems(List<SpotifyArtist> items) {
	}

	private record SpotifyArtist(String id, List<String> genres, SpotifyExternalUrls external_urls,
			List<SpotifyImage> images) {
	}

	private record SpotifyImage(String url) {
	}

	private record SpotifyExternalUrls(String spotify) {
	}

	private record SpotifyTopTracksResponse(List<SpotifyTrack> tracks) {
	}

	private record SpotifyTrack(String name) {
	}

	private record SpotifyPlaylistSearchResponse(SpotifyPlaylistItems playlists) {
	}

	private record SpotifyPlaylistItems(List<SpotifyPlaylist> items) {
	}

	private record SpotifyPlaylist(SpotifyExternalUrls external_urls) {
	}
}
