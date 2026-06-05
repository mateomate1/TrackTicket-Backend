package es.metrica.trackticket.dto.mapper;

import java.time.LocalDate;
import java.util.List;

import es.metrica.trackticket.dto.ConcertResponseDTO;
import es.metrica.trackticket.dto.VenueDTO;
import es.metrica.trackticket.models.Address;
import es.metrica.trackticket.models.City;
import es.metrica.trackticket.models.Concert;
import es.metrica.trackticket.models.Country;
import es.metrica.trackticket.models.Location;
import es.metrica.trackticket.models.State;
import es.metrica.trackticket.models.Venue;

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

		return new ConcertResponseDTO(idConcert, nameConcert, concertDate, sellLink, artistName, artistGenre,
				artistLink, new VenueDTO(venueName, latitude, longitude, address, stateName, countryName));
	}

	public static Concert mapToConcert(TicketMasterEvent event) {
		String externalId = event.id();
		String concertName = event.name();
		LocalDate concertDate = LocalDate.parse(event.dates().start().localDate());
		String sellLink = event.url();
		Country country = new Country(event._embedded().venues().getFirst().country().name());
		State state = new State(event._embedded().venues().getFirst().state().name(), country);
		City city = new City(event._embedded().venues().getFirst().city().name(), state);
		String line2 = "";
		if (event._embedded().venues().getFirst().address().line2() != null) {
			line2 = event._embedded().venues().getFirst().address().line2();
		}
		Address address = new Address(event._embedded().venues().getFirst().address().line1(), line2,
				event._embedded().venues().getFirst().postalCode(), city);
		Location location = new Location(Double.valueOf(event._embedded().venues().getFirst().location().latitude()),
				Double.valueOf(event._embedded().venues().getFirst().location().longitude()));
		Venue venue = new Venue(event._embedded().venues().getFirst().name(), location, address);

		return new Concert(externalId, concertName, concertDate, sellLink, venue);
	}

	public static String getConcertId(TicketMasterEvent event) {
		return event.id();
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

	public record TicketMasterDates(TicketMasterStart start) {
	}

	public record TicketMasterStart(String localDate) {
	}

	public record TicketMasterEmbeddedVenues(List<TicketMasterVenue> venues,
			List<TickerMasterAttractions> attractions) {
	}

	private record TickerMasterAttractions(String name, String url) {
	}

	public record TicketMasterVenue(String name, String postalCode, TicketMasterLocation location,
			TicketMasterAddress address, TicketMasterCity city, TicketMasterState state, TicketMasterCountry country) {
	}

	private record TicketMasterAddress(String line1, String line2) {
	}

	private record TicketMasterCity(String name) {
	}

	public record TicketMasterState(String name) {
	}

	private record TicketMasterCountry(String name) {
	}

	public record TicketMasterLocation(String longitude, String latitude) {
	}
}