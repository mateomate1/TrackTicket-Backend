package es.metrica.trackticket.dto;

import jakarta.validation.constraints.NotNull;

public record VenueDTO(
		
		@NotNull
		String venueName,
		
		@NotNull
		Double venueLocation,
		
		@NotNull
		String venueAddress,
		
		@NotNull
		String venueState,
		
		@NotNull
		String venueCountry
		
		) {

}
