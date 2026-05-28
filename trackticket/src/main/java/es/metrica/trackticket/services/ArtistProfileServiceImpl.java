package es.metrica.trackticket.services;

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
	SpotifyTokenService spotifyTokenService;

	public ArtistProfileServiceImpl(RestClient.Builder restClientBuilder, @Value("${spotify.api.url}") String apiUrl,
			SpotifyTokenService spotifyTokenService) {
		this.restClientArtistSearch = restClientBuilder.clone().baseUrl(apiUrl).build();
		this.spotifyTokenService = spotifyTokenService;
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
		}).header("Authorization", "Bearer " + spotifyTokenService.getToken()).retrieve().body(SpotifyArtistSearchResponse.class);

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
		}).header("Authorization", "Bearer " + spotifyTokenService.getToken()).retrieve().body(SpotifyAlbumsResponse.class);

		if (response == null || response.items().isEmpty()) {
			throw new ResourceNotFoundException("No albums found for that artist");
		}

		return response.items().stream().map(SpotifyAlbum::name).toList();
	}

	private String getPlaylist(String artistName) {

		SpotifyPlaylistSearchResponse response = restClientArtistSearch.get().uri(uriBuilder -> {
			uriBuilder.path("/search").queryParam("q", "This is " + artistName).queryParam("type", "playlist")
					.queryParam("market", "ES").queryParam("limit", 2);
			return uriBuilder.build();
		}).header("Authorization", "Bearer " + spotifyTokenService.getToken()).retrieve().body(SpotifyPlaylistSearchResponse.class);

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
