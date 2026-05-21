package es.metrica.trackticket.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.annotation.JsonProperty;

import es.metrica.trackticket.dto.ConcertResponseDTO;
import es.metrica.trackticket.dto.ConcertSearchRequestDTO;
import es.metrica.trackticket.dto.VenueDTO;
import es.metrica.trackticket.services.SearchServiceImpl.TicketMasterEvent;

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

					.queryParam("locale", "es").queryParam("keyword", dto.artista())
					.queryParam("startDateTime", dto.date()) // String fecha inicio
					.queryParam("size", 20);

			if (dto.date() == null) { //fechaFin
				uriBuilder.queryParam("endDateTime", "fechaInicio hora 23:59"); // string fecha fin
			} else {
				uriBuilder.queryParam("endDateTime", dto.date()); // fechaFin
			}

			if (dto.venue().venueAddress() != null) { // tiene que ser city
				uriBuilder.queryParam("city", dto.venue().venueAddress());
			}

			if (dto.artista() != null) {
				uriBuilder.queryParam("keyword", dto.artista());
			}
			return uriBuilder.build();
		}).retrieve().body(TicketMasterResponse.class);

		return response._embedded.events().stream().map(this::mapToConcertResponseDTO).toList();
	}

	private boolean isValidRequest(ConcertSearchRequestDTO dto) {
		if (dto.date() == null) { // fechaInicio
			return false;
		}

		if (dto.artista() == null && dto.venue().venueAddress() == null) { // city, no venue
			return false;
		}

		return true;
	}

	private ConcertResponseDTO mapToConcertResponseDTO(TicketMasterEvent event) {

		String idConcert = "";
		String nameConcert = "";
		double latitude = 0.0;
		double longitude = 0.0;
		String venueName = "";
		LocalDateTime concertDate = null;
		String address = ""; // dirección completa con todo
		String sellLink = "";

		idConcert = event.id();
		nameConcert = event.name();
		latitude = Double.parseDouble(event._embedded().venues().get(0).location().latitude());
		longitude = Double.parseDouble(event._embedded().venues().get(0).location().longitude());
		venueName = event._embedded().venues().getFirst().name();
		concertDate = LocalDateTime.parse(event.dates().start().dateTime().replace("Z", "")).plusHours(2); // hora
																											// de
																											// MAdrid
		StringBuilder addressBuilder = new StringBuilder(event._embedded().venues().getFirst().address().line1());

		if (event._embedded().venues().getFirst().address().line2() != null) {
			addressBuilder.append(", ").append(event._embedded().venues().getFirst().address().line2());
		}

		addressBuilder.append(", ").append(event._embedded().venues().getFirst().postalCode());
		addressBuilder.append(", ").append(event._embedded().venues().getFirst().city().name());
		addressBuilder.append(", ").append(event._embedded().venues().getFirst().state().name());
		addressBuilder.append(", ").append(event._embedded().venues().getFirst().country().name());

		address = addressBuilder.toString();

		sellLink = event.url();

		return new ConcertResponseDTO();
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
