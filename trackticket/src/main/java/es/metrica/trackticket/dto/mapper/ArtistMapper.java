package es.metrica.trackticket.dto.mapper;

import java.util.List;

import es.metrica.trackticket.dto.ArtistResponseDTO;
import es.metrica.trackticket.models.Artist;

public class ArtistMapper {

	public static ArtistResponseDTO fromSpotifyToArtistResponseDTO(String name, String genre,
			SpotifyArtistSearchResponse response, List<String> albums, String playlistUrl) {

		return new ArtistResponseDTO(response.artists().items().getFirst().id(), name,
				response.artists().items().getFirst().external_urls().spotify(), playlistUrl,
				response.artists().items().getFirst().images().getFirst().url(), genre, albums);
	}

	public static Artist mapToArtistWithId(SpotifyGetArtistResponse response, String externalIdArtist,
			String musicGenre) {

		Artist artist = new Artist(externalIdArtist, response.name());

		artist.setMusicGenre(musicGenre);
		artist.setSpotifyLink(response.external_urls().spotify());
		artist.setArtistImageUrl(response.images().getFirst().url());

		return artist;
	}

	public static Artist mapToArtistWithName(String name, String genre, SpotifyArtistSearchResponse response) {

		Artist artist = new Artist(response.artists().items().getFirst().id(), name);

		artist.setMusicGenre(genre);
		artist.setSpotifyLink(response.artists().items().getFirst().external_urls().spotify());
		artist.setArtistImageUrl(response.artists().items().getFirst().images().getFirst().url());

		return artist;
	}

	public static ArtistResponseDTO mapFromArtistToArtistResponseDTO(Artist artist) {

		return new ArtistResponseDTO(artist.getExternalIdArtist(), artist.getArtistName(), artist.getSpotifyLink(),
				artist.getPlaylistLink(), artist.getArtistImageUrl(), artist.getMusicGenre(), artist.getAlbums());
	}

	public record SpotifyGetArtistResponse(String name, List<SpotifyImage> images, SpotifyExternalUrls external_urls) {
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
