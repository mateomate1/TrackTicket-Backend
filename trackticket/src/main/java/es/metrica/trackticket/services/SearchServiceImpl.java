package es.metrica.trackticket.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import es.metrica.trackticket.dto.ConcertResponseDTO;
import es.metrica.trackticket.dto.ConcertSearchRequestDTO;
import es.metrica.trackticket.dto.VenueDTO;

@Service
public class SearchServiceImpl implements SearchService {

	private RestClient restClient;
	private String apiKey;

	public SearchServiceImpl(RestClient.Builder restClientBuilder, @Value("${ticketmaster.api.url}") String url,
			@Value("${ticketmaster.api.key}") String apiKey) {
		this.apiKey = apiKey;
		this.restClient = restClientBuilder.baseUrl(url).build();
	}

	@Override
	public List<ConcertResponseDTO> searchConcerts(ConcertSearchRequestDTO dto) {

		if (!isValidRequest(dto)) {
			throw new IllegalArgumentException(
					"Los parámetros de búsqueda no son válidos. Debe haber fecha y artista y/o ciudad.");
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

		TicketMasterResponse response = restClient.get().uri(uriBuilder -> {
			uriBuilder.path("/events.json").queryParam("apikey", this.apiKey).queryParam("locale", "es")
					.queryParam("startDateTime", dto.startDate().format(formatter)).queryParam("includeTBA", "no")
					.queryParam("includeTBD", "no").queryParam("size", 20).queryParam("sort", "date,asc");

			if (dto.finalDay() != null) {
				uriBuilder.queryParam("endDateTime", dto.finalDay().format(formatter));
			}

			if (dto.location() != null && !dto.location().isEmpty()) {
				uriBuilder.queryParam("city", dto.location());
			}

			if (dto.artist() != null && !dto.artist().isBlank()) {
				uriBuilder.queryParam("keyword", dto.artist());
			}

			return uriBuilder.build();
		}).retrieve().body(TicketMasterResponse.class);

		if (response != null && response._embedded() != null) {
			try {
				return response._embedded().events().stream().map(this::mapToConcertResponseDTO).toList();
			} catch (NullPointerException e) {
				return List.of();
			}
		} else {
			return List.of();
		}
	}

	private boolean isValidRequest(ConcertSearchRequestDTO dto) {
		if (dto.startDate() == null) {
			return false;
		}

		if ((dto.artist() == null || dto.artist().isBlank()) && (dto.location() == null || dto.location().isBlank())) {
			return false;
		}

		return true;
	}

	private ConcertResponseDTO mapToConcertResponseDTO(TicketMasterEvent event) {

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

		StringBuilder addressBuilder = new StringBuilder(event._embedded().venues().getFirst().address().line1());

		if (event._embedded().venues().getFirst().address().line2() != null) {
			addressBuilder.append(", ").append(event._embedded().venues().getFirst().address().line2());
		}

		addressBuilder.append(", ").append(event._embedded().venues().getFirst().postalCode());
		addressBuilder.append(", ").append(event._embedded().venues().getFirst().city().name());

		String address = addressBuilder.toString();

		return new ConcertResponseDTO(idConcert, nameConcert, concertDate, sellLink, artistName,
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

	private record TickerMasterAttractions(String name) {
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
