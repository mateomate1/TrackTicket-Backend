package es.metrica.trackticket.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import es.metrica.trackticket.dto.ArtistResponseDTO;
import es.metrica.trackticket.dto.FavouriteArtistRequestDTO;
import es.metrica.trackticket.dto.TokenRequestDTO;
import es.metrica.trackticket.dto.mapper.ArtistMapper;
import es.metrica.trackticket.dto.mapper.ArtistMapper.SpotifyGetArtistResponse;
import es.metrica.trackticket.models.Artist;
import es.metrica.trackticket.repositories.ArtistRepository;
import es.metrica.trackticket.repositories.UserRepository;

@Service
public class FavouriteArtistServiceImpl implements FavouriteArtistService {

	private RestClient restClientArtistSearch;
	private UserRepository userRepository;
	private ArtistRepository artistRepository;
	private SpotifyTokenService spotifyTokenService;

	public FavouriteArtistServiceImpl(RestClient.Builder restClientBuilder, @Value("${spotify.api.url}") String apiUrl,
			SpotifyTokenService spotifyTokenService, UserRepository userRepository, ArtistRepository artistRepository) {
		this.restClientArtistSearch = restClientBuilder.clone().baseUrl(apiUrl).build();
		this.spotifyTokenService = spotifyTokenService;
		this.userRepository = userRepository;
		this.artistRepository = artistRepository;
	}

	@Override
	public List<ArtistResponseDTO> getFavouriteArtists(TokenRequestDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addFavouriteArtist(FavouriteArtistRequestDTO dto) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteFavouriteArtist(FavouriteArtistRequestDTO dto) {
		// TODO Auto-generated method stub

	}

	public Artist getArtistFromSpotifyAndSave(String spotifyId, String artistGenre) {

		Artist artist = artistRepository.findByExternalIdArtist(spotifyId).orElse(null);

		if (artist != null) {
			return artist;
		}

		SpotifyGetArtistResponse response = restClientArtistSearch.get().uri("/artists/{id}", spotifyId)
				.header("Authorization", "Bearer " + spotifyTokenService.getToken()).retrieve()
				.body(SpotifyGetArtistResponse.class);
		
		artist = ArtistMapper.mapToArtist(response, spotifyId, artistGenre);

		return artist;

	}

}
