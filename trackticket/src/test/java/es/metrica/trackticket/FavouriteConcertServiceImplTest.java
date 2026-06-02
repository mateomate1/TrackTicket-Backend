package es.metrica.trackticket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClient;

import es.metrica.trackticket.dto.ConcertFavoriteRequestDTO;
import es.metrica.trackticket.dto.ConcertResponseDTO;
import es.metrica.trackticket.dto.TokenRequestDTO;
import es.metrica.trackticket.dto.VenueDTO;
import es.metrica.trackticket.models.Address;
import es.metrica.trackticket.models.Artist;
import es.metrica.trackticket.models.City;
import es.metrica.trackticket.models.Concert;
import es.metrica.trackticket.models.Location;
import es.metrica.trackticket.models.User;
import es.metrica.trackticket.models.Venue;
import es.metrica.trackticket.repositories.AddressRepository;
import es.metrica.trackticket.repositories.CityRepository;
import es.metrica.trackticket.repositories.ConcertRepository;
import es.metrica.trackticket.repositories.CountryRepository;
import es.metrica.trackticket.repositories.LocationRepository;
import es.metrica.trackticket.repositories.StateRepository;
import es.metrica.trackticket.repositories.UserRepository;
import es.metrica.trackticket.repositories.VenueRepository;
import es.metrica.trackticket.services.FavouriteConcertServiceImpl;
import es.metrica.trackticket.services.FindAndSaveArtistServiceImpl;

@ExtendWith(MockitoExtension.class)
class FavouriteConcertServiceImplTest {
	@Mock private CountryRepository countryRespository;
	@Mock private StateRepository staterepository;
	@Mock private UserRepository userRepository;
	@Mock private ConcertRepository concertRepository;
	@Mock private VenueRepository venueRepository;
	@Mock private CityRepository cityRepository;
	@Mock private LocationRepository locationRepository;
	@Mock private AddressRepository addressRepository;
	@Mock private FindAndSaveArtistServiceImpl findAndSaveArtistService;
	@Mock private RestClient.Builder restClientBuilder;
	@Mock private RestClient restClient;
	@Mock private User userMock;
	@Mock private Concert concertMock;
	private FavouriteConcertServiceImpl favouriteConcertService;


	@BeforeEach
	void setUp() {

		when(restClientBuilder.baseUrl(anyString())).thenReturn(restClientBuilder);
		when(restClientBuilder.build()).thenReturn(restClient);

		favouriteConcertService = new FavouriteConcertServiceImpl(
				countryRespository, 
				staterepository, 
				cityRepository, 
				addressRepository,
				userRepository, 
				concertRepository, 
				venueRepository, 
				locationRepository,
				findAndSaveArtistService, 
				restClientBuilder, 
				"https://app.ticketmaster.com/discovery/v2", 
				"uDn8Th1Hg0DWXaInqHaQ2gQ7s1VJ0Ru9"
		);
	}
	
	@Test
	@DisplayName("Tests if service correctly calls userRepository to save the new favourite concert")
	void addFavConcert() {
		String token = "1234";
		String idConcierto = "ticketmasterId123";
		ConcertFavoriteRequestDTO dto = new ConcertFavoriteRequestDTO(token, idConcierto);

		User user = new User("usuarioTest", "test@email.com", "password123");
		Venue venue = new Venue(); 
		Concert concert = new Concert(idConcierto, "Concierto de Prueba", LocalDate.now(), "link.com", venue);

		when(userRepository.findByUserSession(token)).thenReturn(Optional.of(user));
		when(concertRepository.findByExternalIdConcert(idConcierto)).thenReturn(Optional.of(concert));

		favouriteConcertService.addFavConcert(dto);

		verify(userRepository).findByUserSession(token);
		verify(concertRepository).findByExternalIdConcert(idConcierto);
		verify(userRepository).save(user); 
	}
	
	@Test
	@DisplayName("Tests if service correctly calls userRepository with a valid token and returns the actual favourite concerts list")
	void getFavConcertList() {
		String token = "1244";
		TokenRequestDTO dto = new TokenRequestDTO(token);

		Location loc1 = new Location(1L, 40.4168, -3.7038);
		loc1.setState("Madrid");
		loc1.setCountry("Spain");
		Address address1 = new Address(1L, "Av. Felipe II", "s/n", "28009", new City());
		Venue venue1 = new Venue(1L, "WiZink Center", loc1, address1);
		Concert firstConcert = new Concert("idConcert1", "Gira 1", LocalDate.of(2026, 6, 1), "link1.com", venue1);
		
		Artist firstArtist = new Artist("idCruzzi", "Cruz Cafuné");
		firstArtist.setMusicGenre("Rap/HipHop");
		firstArtist.setSpotifyLink("perfildespotifyCruzzi.com");
		firstConcert.getArtists().add(firstArtist);

		Location loc2 = new Location(2L, 41.385, 2.173);
		loc2.setState("Catalonia");
		loc2.setCountry("Spain");
		Address address2 = new Address(2L, "Av. de l'Estadi", "50", "08038", new City());
		Venue venue2 = new Venue(2L, "Palau Sant Jordi", loc2, address2);
		Concert secondConcert = new Concert("idConcert2", "Gira 2", LocalDate.of(2026, 7, 15), "link2.com", venue2);

		Artist secondArtist = new Artist("idRels", "Rels B");
		secondArtist.setMusicGenre("Rap/HipHop");
		secondArtist.setSpotifyLink("perfildespotifyrels.com");
		secondConcert.getArtists().add(secondArtist);

		when(userRepository.findByUserSession(token)).thenReturn(Optional.of(userMock));
		when(userMock.getFavouriteConcerts()).thenReturn(List.of(firstConcert, secondConcert));

		List<ConcertResponseDTO> result = favouriteConcertService.getFavConcertList(dto);

		VenueDTO venueDto1 = new VenueDTO("WiZink Center", 40.4168, -3.7038, "Av. Felipe II, s/n, 28009", "Madrid", "Spain");
		ConcertResponseDTO firstResult = new ConcertResponseDTO("idConcert1", "Gira 1", LocalDate.of(2026, 6, 1), "link1.com", "Cruz Cafuné", "Rap/HipHop", "perfildespotifyCruzzi.com", venueDto1);

		VenueDTO venueDto2 = new VenueDTO("Palau Sant Jordi", 41.385, 2.173, "Av. de l'Estadi, 50, 08038", "Catalonia", "Spain");
		ConcertResponseDTO secondResult = new ConcertResponseDTO("idConcert2", "Gira 2", LocalDate.of(2026, 7, 15), "link2.com", "Rels B", "Rap/HipHop", "perfildespotifyrels.com", venueDto2);

		verify(userRepository).findByUserSession(token);
		verify(userMock).getFavouriteConcerts();
		assertEquals(List.of(firstResult, secondResult), result);
	}
	
	
	@Test
	@DisplayName("Tests if service correctly calls userRepository and removes a favourite concert")
	void removeFavConcert() {
		String token = "1234";
		String idConcierto = "ticketmasterId123";
		ConcertFavoriteRequestDTO dto = new ConcertFavoriteRequestDTO(token, idConcierto);

		User user = new User("usuarioTest", "test@email.com", "password123");
		Concert concert = new Concert(idConcierto, "Concierto de Prueba", LocalDate.now(), "link.com", new Venue());
		user.getFavouriteConcerts().add(concert);

		when(userRepository.findByUserSession(token)).thenReturn(Optional.of(user));

		favouriteConcertService.removeFavConcert(dto);

		verify(userRepository).findByUserSession(token);
		verify(userRepository).save(user);
		assertTrue(user.getFavouriteConcerts().isEmpty());
	}
	
	@Test
	@DisplayName("Tests if correctly throws IllegalArgumentException when removing a concert not in list")
	void removeFavConcertNotInFavsError() {
		String token = "1234";
		String idConcierto = "ticketmasterId123";
		
		User user = new User("usuarioTest", "test@email.com", "password123");

		when(userRepository.findByUserSession(token)).thenReturn(Optional.of(user));

		Exception e = assertThrows(IllegalArgumentException.class, 
				() -> favouriteConcertService.removeFavConcert(new ConcertFavoriteRequestDTO(token, idConcierto)));

		verify(userRepository).findByUserSession(token);
		verify(userRepository, never()).save(user);
		assertEquals("El concierto no está en tu lista de favoritos", e.getMessage());
	}
	
	@Test
	@DisplayName("Tests if correctly throws IllegalArgumentException when concert is already in favourites")
	void addFavConcertAlreadyFavError() {
		String token = "1234";
		String idConcierto = "ticketmasterId123";
		
		User user = new User("usuarioTest", "test@email.com", "password123");
		Concert concert = new Concert(idConcierto, "Concierto de Prueba", LocalDate.now(), "link.com", new Venue());
		user.getFavouriteConcerts().add(concert);

		when(userRepository.findByUserSession(token)).thenReturn(Optional.of(user));

		Exception e = assertThrows(IllegalArgumentException.class, 
				() -> favouriteConcertService.addFavConcert(new ConcertFavoriteRequestDTO(token, idConcierto)));

		verify(userRepository).findByUserSession(token);
		verify(userRepository, never()).save(user);
		assertEquals("El concierto ya está en favoritos", e.getMessage());
	}
}
