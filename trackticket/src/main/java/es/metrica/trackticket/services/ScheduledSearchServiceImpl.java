package es.metrica.trackticket.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import es.metrica.trackticket.dto.mapper.ConcertSearchMapper;
import es.metrica.trackticket.dto.mapper.ConcertSearchMapper.TicketMasterEvent;
import es.metrica.trackticket.dto.mapper.ConcertSearchMapper.TicketMasterResponse;
import es.metrica.trackticket.exception.ResourceNotFoundException;
import es.metrica.trackticket.models.Artist;
import es.metrica.trackticket.models.Concert;
import es.metrica.trackticket.models.Notification;
import es.metrica.trackticket.models.NotificationType;
import es.metrica.trackticket.models.User;
import es.metrica.trackticket.models.Venue;
import es.metrica.trackticket.repositories.ArtistRepository;
import es.metrica.trackticket.repositories.ConcertRepository;
import es.metrica.trackticket.repositories.NotificationRepository;
import es.metrica.trackticket.repositories.UserRepository;

@Service
public class ScheduledSearchServiceImpl implements ScheduledSearchService {

	private RestClient restClient;
	private String apiKey;
	private ConcertRepository concertRepository;
	private ArtistRepository artistRepository;
	private UserRepository userRepository;
	private NotificationRepository notificationRepository;

	public ScheduledSearchServiceImpl(RestClient.Builder restClientBuilder,
			@Value("${ticketmaster.api.url}") String url, @Value("${ticketmaster.api.key}") String apiKey,
			ConcertRepository concertRepository, ArtistRepository artistRepository, UserRepository userRepository,
			NotificationRepository notificationRepository) {
		this.apiKey = apiKey;
		this.restClient = restClientBuilder.baseUrl(url).build();
		this.concertRepository = concertRepository;
		this.artistRepository = artistRepository;
		this.userRepository = userRepository;
		this.notificationRepository = notificationRepository;
	}

	@Override
	@Scheduled(cron = "0 0 2 * * *")
	public void deletePastConcerts() {
		concertRepository.deleteByConcertDateBefore(LocalDate.now());
	}

	@Override
	@Scheduled(cron = "0 0 2 * * *")
	public void searchForFavouriteConcerts() {

		List<Concert> allConcerts = concertRepository.findAll();

		for (Concert concert : allConcerts) {

			Concert foundConcert = new Concert(null, null, null, null, null);

			List<User> usersWithFavouriteConcert = userRepository.findByFavouriteConcertsContains(concert);

			boolean hasChanged = false;

			try {
				foundConcert = findFavouriteConcert(concert.getexternalIdConcert());
			} catch (ResourceNotFoundException e) {

				StringBuilder message = new StringBuilder("El concierto de ");
				message.append(concert.getArtists().getFirst().getArtistName()).append(", en ")
						.append(concert.getVenue().getVenueAddress().getCity()).append(", del día ")
						.append(concert.getConcertDate()).append(", ha sido cancelado.");

				for (User user : usersWithFavouriteConcert) {
					notificationRepository
							.save(new Notification(message.toString(), NotificationType.CANCELLED_CONCERT, user));
				}

				concertRepository.deleteById(concert.getIdConcert());
			}

			if (foundConcert.getVenue() != null && !concert.getVenue().equals(foundConcert.getVenue())) {

				StringBuilder message = new StringBuilder("El concierto de ");
				message.append(concert.getArtists().getFirst().getArtistName()).append(", en ")
						.append(concert.getVenue().getVenueAddress().getCity()).append(", del día ")
						.append(concert.getConcertDate()).append(", ha sido movido a: ")
						.append(mapVenueToFullAddress(foundConcert.getVenue()));

				for (User user : usersWithFavouriteConcert) {
					notificationRepository
							.save(new Notification(message.toString(), NotificationType.MODIFIED_CONCERT, user));
				}

				concert.setVenue(foundConcert.getVenue());
				hasChanged = true;
			}

			if (foundConcert.getConcertDate() != null
					&& !concert.getConcertDate().equals(foundConcert.getConcertDate())) {

				StringBuilder message = new StringBuilder("El concierto de ");
				message.append(concert.getArtists().getFirst().getArtistName()).append(", en ")
						.append(concert.getVenue().getVenueAddress().getCity()).append(", del día ")
						.append(concert.getConcertDate()).append(", ha sido cambiado al día: ")
						.append(foundConcert.getConcertDate());

				for (User user : usersWithFavouriteConcert) {
					notificationRepository
							.save(new Notification(message.toString(), NotificationType.MODIFIED_CONCERT, user));
				}

				concert.setConcertDate(foundConcert.getConcertDate());
				hasChanged = true;
			}

			if (hasChanged) {
				concertRepository.save(concert);
			}

		}
	}

	@Override
	@Scheduled(cron = "0 0 2 * * *")
	public void searchForNewConcerts() {

		List<Artist> allArtists = artistRepository.findAll();

		for (Artist artist : allArtists) {

			List<String> allConcertsId = this.getAllConcertsId(artist.getArtistName());

			if (artist.getKnownConcerts().isEmpty()) {
				artist.setKnownConcerts(allConcertsId);
			}

			List<String> newConcerts = new ArrayList<>();

			for (String concertId : allConcertsId) {
				if (!artist.getKnownConcerts().contains(concertId)) {
					newConcerts.add(concertId);
					artist.getKnownConcerts().add(concertId);
				}
			}

			if (!newConcerts.isEmpty()) {

				List<User> usersWithFavouriteArtist = userRepository.findByFavouriteArtistsContains(artist);

				for (User user : usersWithFavouriteArtist) {
					notificationRepository.save(new Notification("Nuevo/s concierto/s de " + artist.getArtistName(),
							NotificationType.NEW_CONCERT, user));
				}

				artistRepository.save(artist);
			}
		}
	}

	private List<String> getAllConcertsId(String artistName) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

		TicketMasterResponse response = restClient.get().uri(uriBuilder -> {
			uriBuilder.path("/events.json").queryParam("apikey", this.apiKey).queryParam("locale", "es")
					.queryParam("keyword", artistName)
					.queryParam("startDateTime", LocalDateTime.now().format(formatter)).queryParam("includeTBA", "no")
					.queryParam("includeTBD", "no").queryParam("size", 50).queryParam("sort", "date,desc");
			return uriBuilder.build();
		}).retrieve().body(TicketMasterResponse.class);

		if (response != null && response._embedded() != null) {
			try {
				return response._embedded().events().stream()
						.filter(event -> event.classifications().getFirst().segment().name().equals("Música"))
						.map(ConcertSearchMapper::getConcertId).toList();
			} catch (NullPointerException e) {
				throw new ResourceNotFoundException("Búsqueda sin resultados");
			}
		} else {
			throw new ResourceNotFoundException("Búsqueda sin resultados");
		}
	}

	private Concert findFavouriteConcert(String concertId) {
		TicketMasterEvent event = restClient.get().uri(
				uriBuilder -> uriBuilder.path("/events/{id}.json").queryParam("apikey", this.apiKey).build(concertId))
				.retrieve().body(TicketMasterEvent.class);

		if (event == null) {
			throw new ResourceNotFoundException("Concierto no encontrado");
		}
		return ConcertSearchMapper.mapToConcert(event);
	}

	private String mapVenueToFullAddress(Venue venue) {
		StringBuilder fullAddress = new StringBuilder(venue.getVenueName());

		fullAddress.append(", en ").append(venue.getVenueAddress().getFirstLine()).append(", ")
				.append(venue.getVenueAddress().getZipCode()).append(", ").append(venue.getVenueAddress().getCity())
				.append(", ").append(venue.getVenueAddress().getCity().getState()).append(", ")
				.append(venue.getVenueAddress().getCity().getState().getCountry());

		return fullAddress.toString();
	}
}
