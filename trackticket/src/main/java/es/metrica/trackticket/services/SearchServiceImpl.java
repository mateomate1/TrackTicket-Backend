package es.metrica.trackticket.services;

import java.time.LocalDateTime;
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

		TicketMasterResponse response = restClient.get().uri(uriBuilder -> {
			uriBuilder.path("/events.json").queryParam("apikey", this.apiKey)

					.queryParam("locale", "es").queryParam("keyword", dto.artist())
					.queryParam("startDateTime", dto.startDate().toString() + "Z")
					.queryParam("size", 20)
					.queryParam("sort", "date,asc");

			if (dto.finalDay() == null) {
				uriBuilder.queryParam("endDateTime", dto.startDate().plusHours(23).plusMinutes(59).toString() + "Z");
			} else {
				uriBuilder.queryParam("endDateTime", dto.finalDay().toString() + "Z");
			}

			if (dto.location() != null) {
				uriBuilder.queryParam("city", dto.location());
			}

			if (dto.artist() != null) {
				uriBuilder.queryParam("keyword", dto.artist());
			}
			return uriBuilder.build();
		}).retrieve().body(TicketMasterResponse.class);

		if(response != null) {
			return response._embedded().events().stream().map(this::mapToConcertResponseDTO).toList();
		} else {
			throw new IllegalArgumentException("Búsqueda sin resultados");
		}
	}

	private boolean isValidRequest(ConcertSearchRequestDTO dto) {
		if (dto.startDate() == null) {
			return false;
		}

		if (dto.artist() == null && dto.location() == null) {
			return false;
		}

		return true;
	}

	private ConcertResponseDTO mapToConcertResponseDTO(TicketMasterEvent event) {

		String idConcert = event.id();
		String nameConcert = event.id();
		double latitude = Double.parseDouble(event._embedded().venues().get(0).location().latitude());
		double longitude = Double.parseDouble(event._embedded().venues().get(0).location().longitude());
		String venueName = event._embedded().venues().getFirst().name();
		LocalDateTime concertDate = LocalDateTime.parse(event.dates().start().dateTime().replace("Z", "")).plusHours(2);
		String stateName = event._embedded().venues().getFirst().state().name();
		String countryName = event._embedded().venues().getFirst().country().name();
		String sellLink = event.url();

		StringBuilder addressBuilder = new StringBuilder(event._embedded().venues().getFirst().address().line1());

		if (event._embedded().venues().getFirst().address().line2() != null) {
			addressBuilder.append(", ").append(event._embedded().venues().getFirst().address().line2());
		}

		addressBuilder.append(", ").append(event._embedded().venues().getFirst().postalCode());
		addressBuilder.append(", ").append(event._embedded().venues().getFirst().city().name());

		String address = addressBuilder.toString();

		return new ConcertResponseDTO(idConcert, nameConcert, concertDate, sellLink,
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

	private record TicketMasterStart(String dateTime) {
	}

	private record TicketMasterEmbeddedVenues(List<TicketMasterVenue> venues) {
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
