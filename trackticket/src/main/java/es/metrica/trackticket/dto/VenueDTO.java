package es.metrica.trackticket.dto;

import jakarta.validation.constraints.NotNull;

public record VenueDTO(
		
		@NotNull
		String venueName,
		
		@NotNull
		String venueLocation,
		
		@NotNull
		String venueAddress,
		
		@NotNull
		String venueState,
		
		@NotNull
		String venueCountry
		
		) {

}
