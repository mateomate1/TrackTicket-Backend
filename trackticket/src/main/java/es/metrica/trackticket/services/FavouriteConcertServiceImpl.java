package es.metrica.trackticket.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import es.metrica.trackticket.dto.ConcertFavoriteRequestDTO;
import es.metrica.trackticket.dto.ConcertResponseDTO;
import es.metrica.trackticket.dto.TokenRequestDTO;
import es.metrica.trackticket.dto.VenueDTO;
import es.metrica.trackticket.models.Address;
import es.metrica.trackticket.models.Artist;
import es.metrica.trackticket.models.Concert;
import es.metrica.trackticket.models.Location;
import es.metrica.trackticket.models.User;
import es.metrica.trackticket.models.Venue;
import es.metrica.trackticket.repositories.ConcertRepository;
import es.metrica.trackticket.repositories.LocationRepository;
import es.metrica.trackticket.repositories.UserRepository;
import es.metrica.trackticket.repositories.VenueRepository;
@Service
public class FavouriteConcertServiceImpl implements FavouriteConcertService{
	private UserRepository userRepository;
    private ConcertRepository concertRepository;
    private VenueRepository venueRepository;
    private LocationRepository locationRepository;
    private EncryptionService encryptionService;
    private FindAndSaveArtistServiceImpl findAndSaveArtistService;
    private RestClient restClient;
    private String apiKey;

    
	public FavouriteConcertServiceImpl(
            UserRepository userRepository,
            ConcertRepository concertRepository,
            VenueRepository venueRepository,
            LocationRepository locationRepository,
            EncryptionService encryptionService,
            FindAndSaveArtistServiceImpl findAndSaveArtistService,
            RestClient.Builder restClientBuilder,
            @Value("${ticketmaster.api.url}") String url,
            @Value("${ticketmaster.api.key}") String apiKey) {
        this.userRepository           = userRepository;
        this.concertRepository        = concertRepository;
        this.venueRepository          = venueRepository;
        this.locationRepository       = locationRepository;
        this.encryptionService        = encryptionService;
        this.findAndSaveArtistService = findAndSaveArtistService;
        this.restClient               = restClientBuilder.baseUrl(url).build();
        this.apiKey                   = apiKey;
    }
	
	
	
	
	
	@Override
	public void addFavConcert(ConcertFavoriteRequestDTO dto) {
		String token = encryptionService.decrypt(dto.token());
		User user = userRepository.findByUserSession(token).orElseThrow(()->new IllegalArgumentException("Sesion invalida"));
		
		boolean alreadyFav = user.getFavouriteConcerts().stream().anyMatch(c->c.getexternalIdConcert().equals(dto.idConcierto()));
		if (alreadyFav) {
            throw new IllegalArgumentException("El concierto ya está en favoritos");
        }
		Concert concierto = concertRepository.findByExternalIdConcert(dto.idConcierto()).orElseGet(()->saveConcert(dto.idConcierto()));
		user.getFavouriteConcerts().add(concierto);
		userRepository.save(user);
		
	}
	
	@Override
	public void removeFavConcert(ConcertFavoriteRequestDTO dto) {
		String token = encryptionService.decrypt(dto.token());
		User user = userRepository.findByUserSession(token).orElseThrow(()->new IllegalArgumentException("Sesion invalida"));
		boolean remove = user.getFavouriteConcerts().removeIf(c->c.getexternalIdConcert().equals(dto.idConcierto()));
		if (!remove) {
			throw new IllegalArgumentException("El concierto no está en tu lista de favoritos");
		}
		userRepository.save(user);
		
		
	}
	
	@Override
	public List<ConcertResponseDTO> getFavConcertList(TokenRequestDTO dto) {
		String token = encryptionService.decrypt(dto.token());
		User user = userRepository.findByUserSession(token).orElseThrow(() -> new IllegalArgumentException("Sesión inválida"));
		return user.getFavouriteConcerts().stream().map(this::mapToConcertResponseDTO).toList();
		
	}
	
	
	
	
	
	
	private Concert saveConcert(String idConcertTicketmaster) {
		
		
        TicketMasterEvent event = restClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/events/{id}.json")
                .queryParam("apikey", this.apiKey)
                .build(idConcertTicketmaster))
            .retrieve()
            .body(TicketMasterEvent.class);

        if (event == null) {
            throw new IllegalArgumentException("Concierto no encontrado en Ticketmaster");
        }

    
        TicketMasterVenue tmVenue = event._embedded().venues().get(0);
        Venue venue = venueRepository.findByVenueName(tmVenue.name())
            .orElseGet(() -> {
                Location location = new Location();
                location.setLatitude(Double.parseDouble(tmVenue.location().latitude()));
                location.setLongitude(Double.parseDouble(tmVenue.location().longitude()));
                location.setState(tmVenue.state().name());
                location.setCountry(tmVenue.country().name());
                locationRepository.save(location);

                Venue newVenue = new Venue();
                newVenue.setVenueName(tmVenue.name());
                newVenue.setVenueLocation(location);
                return venueRepository.save(newVenue);
            });
        
       
        
        LocalDate concertDate = LocalDate.parse(event.dates().start().localDate());

    
        String artistName = event._embedded().attractions().get(0).name();
        
        String artistGenre = null;
        
        if (event.classifications() != null
                && !event.classifications().isEmpty()
                && event.classifications().get(0).genre() != null) {
            artistGenre = event.classifications().get(0).genre().name();
        }

   
        Artist artist = findAndSaveArtistService.getArtistByNameFromSpotifyAndSave(artistName, artistGenre);

      
        Concert concert = new Concert(event.id(),event.name(),concertDate,event.url(),venue);
        concert.getArtists().add(artist);

        return concertRepository.save(concert);
	}
	
	
	
	
	private ConcertResponseDTO mapToConcertResponseDTO(Concert concert) {

		String artistName = concert.getArtists().get(0).getArtistName();
		String artistGenre = concert.getArtists().get(0).getMusicGenre();
		String artistLink = concert.getArtists().get(0).getSpotifyLink();

		Venue venue = concert.getVenue();
		Location loc = venue.getVenueLocation();
		Address address = venue.getVenueAddress();
		
		String fullAddress = address.getFirstLine() + ", " + address.getZipCode() + ", " + address.getCity().getCityName();	
		
		VenueDTO venueDto = new VenueDTO(
				venue.getVenueName(),
				loc.getLatitude(),
				loc.getLongitude(),
				fullAddress,       
				loc.getState(),  
				loc.getCountry()
		);

		return new ConcertResponseDTO(
				concert.getexternalIdConcert(), 
				concert.getConcertName(), 
				concert.getConcertDate(), 
				concert.getSellLink(), 
				artistName, 
				artistGenre, 
				artistLink, 
				venueDto
		);
	}
	
	
	
	
	private record TicketMasterEvent(
	        String id,
	        String name, 
	        String url,
	        TicketMasterDates dates,
	        TicketMasterEmbeddedVenues _embedded,
	        List<TicketMasterClassification> classifications
	    ) {}

    private record TicketMasterDates(TicketMasterStart start) {}

    private record TicketMasterStart(String localDate, String localTime) {}

    private record TicketMasterEmbeddedVenues(
        List<TicketMasterVenue> venues,
        List<TicketMasterAttraction> attractions
    ) {}

    private record TicketMasterVenue(
    		String name, 
    		TicketMasterLocation location,
    		TicketMasterState state,    
    		TicketMasterCountry country   
    ) {}

    private record TicketMasterState(String name) {}
    private record TicketMasterCountry(String name) {}
    private record TicketMasterLocation(String latitude, String longitude) {}
    private record TicketMasterAttraction(String name) {}
    private record TicketMasterClassification(TicketMasterGenre genre) {}
    private record TicketMasterGenre(String name) {}
		
	

}
