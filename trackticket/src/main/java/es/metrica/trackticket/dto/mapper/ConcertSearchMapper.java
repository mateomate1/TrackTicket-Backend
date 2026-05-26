package es.metrica.trackticket.dto.mapper;

import java.time.LocalDate;
import java.util.List;

import es.metrica.trackticket.dto.ConcertResponseDTO;
import es.metrica.trackticket.dto.VenueDTO;

public final class ConcertSearchMapper {

	public static ConcertResponseDTO mapToConcertResponseDTO(TicketMasterEvent event) {

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
		String artistGenre = event.classifications().getFirst().genre().name();
		String artistLink = event._embedded().attractions().getFirst().url();

		StringBuilder addressBuilder = new StringBuilder(event._embedded().venues().getFirst().address().line1());

		if (event._embedded().venues().getFirst().address().line2() != null) {
			addressBuilder.append(", ").append(event._embedded().venues().getFirst().address().line2());
		}

		addressBuilder.append(", ").append(event._embedded().venues().getFirst().postalCode());
		addressBuilder.append(", ").append(event._embedded().venues().getFirst().city().name());

		String address = addressBuilder.toString();

		return new ConcertResponseDTO(idConcert, nameConcert, concertDate, sellLink, artistName, artistGenre, artistLink,
				new VenueDTO(venueName, latitude, longitude, address, stateName, countryName));
	}

	public record TicketMasterResponse(TicketMasterEmbedded _embedded) {
	}

	public record TicketMasterEmbedded(List<TicketMasterEvent> events) {
	}

	public record TicketMasterEvent(String id, String name, String url, TicketMasterDates dates,
			List<TicketMasterClassifications> classifications, TicketMasterEmbeddedVenues _embedded) {
	}

	public record TicketMasterClassifications(TicketMasterSegment segment, TicketMasterGenre genre) {
	}

	public record TicketMasterSegment(String name) {
	}

	private record TicketMasterGenre(String name) {
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
