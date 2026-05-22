package es.metrica.trackticket.dto;

import jakarta.validation.constraints.NotNull;

public record VenueDTO(
		
		@NotNull
		String venueName,
		
		@NotNull
		Double latitude,
		
		@NotNull
		Double longitude,
		
		@NotNull
		String venueAddress,
		
		@NotNull
		String venueState,
		
		@NotNull
		String venueCountry
		
		) {

}
