package es.metrica.trackticket.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import es.metrica.trackticket.dto.ArtistResponseDTO;
import es.metrica.trackticket.dto.FavouriteArtistRequestDTO;
import es.metrica.trackticket.dto.TokenRequestDTO;
import es.metrica.trackticket.dto.mapper.ArtistMapper;
import es.metrica.trackticket.exception.NotLoggedInException;
import es.metrica.trackticket.exception.ResourceNotFoundException;
import es.metrica.trackticket.models.Artist;
import es.metrica.trackticket.models.User;
import es.metrica.trackticket.repositories.ArtistRepository;
import es.metrica.trackticket.repositories.UserRepository;

@Service
public class FavouriteArtistServiceImpl implements FavouriteArtistService {

	private RestClient restClientArtistSearch;
	private UserRepository userRepository;
	private ArtistRepository artistRepository;
	private SpotifyTokenService spotifyTokenService;
	private FindAndSaveArtistService findAndSaveArtistService;
	private UserService userService;

	public FavouriteArtistServiceImpl(RestClient.Builder restClientBuilder, @Value("${spotify.api.url}") String apiUrl,
			SpotifyTokenService spotifyTokenService, UserRepository userRepository, ArtistRepository artistRepository,
			FindAndSaveArtistService findAndSaveArtistService, UserService userService) {
		this.restClientArtistSearch = restClientBuilder.clone().baseUrl(apiUrl).build();
		this.spotifyTokenService = spotifyTokenService;
		this.userRepository = userRepository;
		this.artistRepository = artistRepository;
		this.findAndSaveArtistService = findAndSaveArtistService;
		this.userService = userService;
	}

	@Transactional(readOnly = true)
	@Override
	public List<ArtistResponseDTO> getFavouriteArtists(TokenRequestDTO dto) {
		User user = userRepository.findByUserSession(dto.token())
				.orElseThrow(() -> new NotLoggedInException("Not a valid token"));

		return user.getFavouriteArtists().stream().map(ArtistMapper::mapFromArtistToArtistResponseDTO).toList();
	}

	@Override
	public void addFavouriteArtist(FavouriteArtistRequestDTO dto) {

		Artist artist = findAndSaveArtistService.getArtistFromSpotifyAndSave(dto.idArtist(), dto.artistGenre());

		userService.addFavouriteArtist(dto.token(), artist);
	}

	@Transactional
	@Override
	public void deleteFavouriteArtist(FavouriteArtistRequestDTO dto) {
		User user = userRepository.findByUserSession(dto.token())
				.orElseThrow(() -> new NotLoggedInException("Not a valid token"));

		Artist artist = artistRepository.findByExternalIdArtist(dto.idArtist())
				.orElseThrow(() -> new ResourceNotFoundException("The artist is not in our database"));

		user.getFavouriteArtists().remove(artist);

		userRepository.save(user);
	}

}
