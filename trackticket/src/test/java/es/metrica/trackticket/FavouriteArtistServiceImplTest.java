package es.metrica.trackticket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import es.metrica.trackticket.dto.ArtistResponseDTO;
import es.metrica.trackticket.dto.FavouriteArtistRequestDTO;
import es.metrica.trackticket.dto.TokenRequestDTO;
import es.metrica.trackticket.exception.NotLoggedInException;
import es.metrica.trackticket.exception.ResourceNotFoundException;
import es.metrica.trackticket.models.Artist;
import es.metrica.trackticket.models.User;
import es.metrica.trackticket.repositories.ArtistRepository;
import es.metrica.trackticket.repositories.UserRepository;
import es.metrica.trackticket.services.FavouriteArtistServiceImpl;
import es.metrica.trackticket.services.FindAndSaveArtistService;
import es.metrica.trackticket.services.UserService;

@ExtendWith(MockitoExtension.class)
class FavouriteArtistServiceImplTest {

	@Mock
	private UserRepository userRepository;
	@Mock
	private ArtistRepository artistRepository;
	@Mock
	private FindAndSaveArtistService findAndSaveArtistService;
	@Mock
	private UserService userService;
	@Mock
	private User user;
	@InjectMocks
	private FavouriteArtistServiceImpl favouriteArtistService;

	@Test
	@DisplayName("Tests if service correctly calls userSevice")
	void addFavouriteArtistHappyPath() {

		String token = "1234";
		String spotifyId = "cruzziId";
		FavouriteArtistRequestDTO dto = new FavouriteArtistRequestDTO(token, spotifyId, "Rap/HipHop");

		Artist artist = new Artist(spotifyId, "Cruz Cafuné");
		when(findAndSaveArtistService.getArtistFromSpotifyAndSave(spotifyId, "Rap/HipHop")).thenReturn(artist);

		favouriteArtistService.addFavouriteArtist(dto);

		verify(userService).addFavouriteArtist(token, artist);
	}

	@Test
	@DisplayName("Tests if service correctly calls userRepository with a valid token and returns the actual favourite artists list")
	void getFavouriteArtistsHappyPath() {
		String token = "1244";
		TokenRequestDTO dto = new TokenRequestDTO(token);

		Artist firstArtist = new Artist("idCruzzi", "Cruz Cafuné");
		firstArtist.setAlbums(List.of("albumCruzzi1", "albumCruzzi2"));
		firstArtist.setArtistImageUrl("urlImageCruzzi");
		firstArtist.setMusicGenre("Rap/HipHop");
		firstArtist.setSpotifyLink("perfildespotifyCruzzi.com");
		firstArtist.setPlaylistLink("playlistdespotifyCruzzi.com");
		Artist secondArtist = new Artist("idRels", "Rels B");
		secondArtist.setAlbums(List.of("albumRels1", "albumRels2"));
		secondArtist.setArtistImageUrl("urlImageRels");
		secondArtist.setMusicGenre("Rap/HipHop");
		secondArtist.setSpotifyLink("perfildespotifyrels.com");
		secondArtist.setPlaylistLink("playlistdespotifyrels.com");

		when(userRepository.findByUserSession(token)).thenReturn(Optional.of(user));
		when(user.getFavouriteArtists()).thenReturn(List.of(firstArtist, secondArtist));

		List<ArtistResponseDTO> result = favouriteArtistService.getFavouriteArtists(dto);
		ArtistResponseDTO firstResult = new ArtistResponseDTO("idCruzzi", "Cruz Cafuné", "perfildespotifyCruzzi.com",
				"playlistdespotifyCruzzi.com", "urlImageCruzzi", "Rap/HipHop", List.of("albumCruzzi1", "albumCruzzi2"));
		ArtistResponseDTO secondResult = new ArtistResponseDTO("idRels", "Rels B", "perfildespotifyrels.com",
				"playlistdespotifyrels.com", "urlImageRels", "Rap/HipHop", List.of("albumRels1", "albumRels2"));

		verify(userRepository).findByUserSession(token);
		verify(user).getFavouriteArtists();
		assertEquals(List.of(firstResult, secondResult), result);
	}

	@Test
	@DisplayName("Tests if service correctly calls the repositories and the favourite artists list of the user")
	void deleteFavouriteArtistHappyPath() {
		when(userRepository.findByUserSession("1234")).thenReturn(Optional.of(user));
		Artist artist = new Artist("idArtist", "artistName");
		when(artistRepository.findByExternalIdArtist("idArtist")).thenReturn(Optional.of(artist));

		favouriteArtistService.deleteFavouriteArtist(new FavouriteArtistRequestDTO("1234", "idArtist", "artistGenre"));

		verify(userRepository).findByUserSession("1234");
		verify(artistRepository).findByExternalIdArtist("idArtist");
		verify(user).getFavouriteArtists();
		verify(userRepository).save(user);
	}

	@Test
	@DisplayName("Tests if correctly throws NotLoggedInException when the user is not found by token")
	void getFavouriteArtistsUserError() {
		when(userRepository.findByUserSession("1234")).thenReturn(Optional.empty());

		Exception e = assertThrows(NotLoggedInException.class,
				() -> favouriteArtistService.getFavouriteArtists(new TokenRequestDTO("1234")));

		verify(userRepository).findByUserSession("1234");
		assertEquals("Not a valid token", e.getMessage());
	}

	@Test
	@DisplayName("Tests if correctly throws NotLoggedInException when the user is not found by token")
	void deleteFavouriteArtistUserError() {
		when(userRepository.findByUserSession("1234")).thenReturn(Optional.empty());

		Exception e = assertThrows(NotLoggedInException.class, () -> favouriteArtistService
				.deleteFavouriteArtist(new FavouriteArtistRequestDTO("1234", "idArtist", "artistGenre")));

		verify(userRepository).findByUserSession("1234");
		assertEquals("Not a valid token", e.getMessage());
	}

	@Test
	@DisplayName("Tests if correctly throws ResourceNotFoundException when the user is not found by token")
	void deleteFavouriteArtistArtistError() {
		when(userRepository.findByUserSession("1234")).thenReturn(Optional.of(user));
		when(artistRepository.findByExternalIdArtist("idArtist")).thenReturn(Optional.empty());

		Exception e = assertThrows(ResourceNotFoundException.class, () -> favouriteArtistService
				.deleteFavouriteArtist(new FavouriteArtistRequestDTO("1234", "idArtist", "artistGenre")));

		verify(userRepository).findByUserSession("1234");
		verify(artistRepository).findByExternalIdArtist("idArtist");
		assertEquals("The artist is not in our database", e.getMessage());
	}
}
