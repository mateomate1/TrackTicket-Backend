package es.metrica.trackticket.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import es.metrica.trackticket.dto.ConcertDetailsResponseDTO;

import es.metrica.trackticket.dto.VenueDTO;

@Service
public class DetailsServiceImpl implements DetailsService{

	private RestClient restClient;
    private String apiKey;

 
    public DetailsServiceImpl(RestClient.Builder restClientBuilder,
            @Value("${ticketmaster.api.url}") String url,
            @Value("${ticketmaster.api.key}") String apiKey) {
        this.apiKey = apiKey;
        this.restClient = restClientBuilder.baseUrl(url).build();
    }
    
    @Override
	public ConcertDetailsResponseDTO detailsConcert(String idConcertTicketMaster) {
    	TicketMasterEvent event = restClient.get()
                .uri(uriBuilder -> uriBuilder
                    .path("/events/{id}.json")
                    .queryParam("apikey", this.apiKey)
                    .build(idConcertTicketMaster)) 
                .retrieve()
                .body(TicketMasterEvent.class);

            if (event == null) {
                throw new IllegalArgumentException("Concierto no encontrado");
            }
		return mapToConcertResponseDTO(event);
	}
    
	private ConcertDetailsResponseDTO mapToConcertResponseDTO(TicketMasterEvent event) {

		String idConcert = event.id();
		String nameConcert = event.name();
		double latitude = Double.parseDouble(event._embedded().venues().get(0).location().latitude());
		double longitude = Double.parseDouble(event._embedded().venues().get(0).location().longitude());
		String venueName = event._embedded().venues().getFirst().name();
		LocalDate concertDate = LocalDate.parse(event.dates().start().localDate());
		String stateName = event._embedded().venues().getFirst().state().name();
		String countryName = event._embedded().venues().getFirst().country().name();
		String sellLink = event.url();
		String artistName = event._embedded().attractions().get(0).name();
		String artistLink = event._embedded().attractions().get(0).url();

		StringBuilder addressBuilder = new StringBuilder(event._embedded().venues().getFirst().address().line1());

		if (event._embedded().venues().getFirst().address().line2() != null) {
			addressBuilder.append(", ").append(event._embedded().venues().getFirst().address().line2());
		}

		addressBuilder.append(", ").append(event._embedded().venues().getFirst().postalCode());
		addressBuilder.append(", ").append(event._embedded().venues().getFirst().city().name());

		String address = addressBuilder.toString();

		return new ConcertDetailsResponseDTO(idConcert, nameConcert, concertDate, sellLink, artistName,artistLink,
				new VenueDTO(venueName, latitude, longitude, address, stateName, countryName));
	}

	private record TicketMasterResponse(TicketMasterEmbedded _embedded) {
	}

	private record TicketMasterEmbedded(List<TicketMasterEvent> events) {
	}

	public record TicketMasterEvent(String id, String name, String url, TicketMasterDates dates,
			TicketMasterEmbeddedVenues _embedded) {
	}

	private record TicketMasterDates(TicketMasterStart start) {
	}

	private record TicketMasterStart(String localDate) {
	}

	private record TicketMasterEmbeddedVenues(List<TicketMasterVenue> venues,
			List<TickerMasterAttractions> attractions) {
	}

	private record TickerMasterAttractions(String name, String url) {
	}

	private record TicketMasterVenue(String name, String postalCode, TicketMasterLocation location,
			TicketMasterAddress address, TicketMasterCity city, TicketMasterState state, TicketMasterCountry country) {
	}

	private record TicketMasterAddress(String line1, String line2) {
	}

	private record TicketMasterCity(String name) {
	}

	private record TicketMasterState(String name) {
	}

	private record TicketMasterCountry(String name) {
	}

	private record TicketMasterLocation(String longitude, String latitude) {
	}

	
}



