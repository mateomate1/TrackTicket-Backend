package es.metrica.trackticket.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import es.metrica.trackticket.dto.ArtistResponseDTO;
import es.metrica.trackticket.dto.FavouriteArtistRequestDTO;
import es.metrica.trackticket.dto.TokenRequestDTO;
import es.metrica.trackticket.repositories.ArtistRepository;
import es.metrica.trackticket.repositories.UserRepository;

@Service
public class FavouriteArtistServiceImpl implements FavouriteArtistService {

	private RestClient restClientArtistSearch;
	private UserRepository userRepository;
	private ArtistRepository artistRepository;
	private SpotifyTokenService spotifyTokenService;
	private FindAndSaveArtistService findAndSaveArtistService;

	public FavouriteArtistServiceImpl(RestClient.Builder restClientBuilder, @Value("${spotify.api.url}") String apiUrl,
			SpotifyTokenService spotifyTokenService, UserRepository userRepository, ArtistRepository artistRepository,
			FindAndSaveArtistService findAndSaveArtistService) {
		this.restClientArtistSearch = restClientBuilder.clone().baseUrl(apiUrl).build();
		this.spotifyTokenService = spotifyTokenService;
		this.userRepository = userRepository;
		this.artistRepository = artistRepository;
		this.findAndSaveArtistService = findAndSaveArtistService;
	}

	@Transactional(readOnly = true)
	@Override
	public List<ArtistResponseDTO> getFavouriteArtists(TokenRequestDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addFavouriteArtist(FavouriteArtistRequestDTO dto) {
		// TODO Auto-generated method stub
	}

	@Transactional
	@Override
	public void deleteFavouriteArtist(FavouriteArtistRequestDTO dto) {
		// TODO Auto-generated method stub
	}

}
