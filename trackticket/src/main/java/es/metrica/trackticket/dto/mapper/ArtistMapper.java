package es.metrica.trackticket.dto.mapper;

import java.util.List;

import es.metrica.trackticket.dto.ArtistResponseDTO;

public class ArtistMapper {

	public static ArtistResponseDTO mapToArtistResponseDTO(String name, String genre, SpotifyArtistSearchResponse response,
			List<String> albums, String playlistUrl) {

		return new ArtistResponseDTO(response.artists().items().getFirst().id(), name, playlistUrl,
				response.artists().items().getFirst().images().getFirst().url(), genre, albums);
	}

	public record SpotifyArtistSearchResponse(SpotifyArtistItems artists) {
	}

	public record SpotifyArtistItems(List<SpotifyArtist> items) {
	}

	public record SpotifyArtist(String id, List<String> genres, SpotifyExternalUrls external_urls,
			List<SpotifyImage> images) {
	}

	private record SpotifyImage(String url) {
	}

	private record SpotifyExternalUrls(String spotify) {
	}

}
