package es.metrica.trackticket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestClient;

import es.metrica.trackticket.dto.ConcertResponseDTO;
import es.metrica.trackticket.dto.VenueDTO;
import es.metrica.trackticket.services.DetailsServiceImpl;

class DetailsServiceImplTest {
	private DetailsServiceImpl detailsService;
	private MockRestServiceServer mockServer;

	@BeforeEach
	void setUp() {

		RestClient.Builder builder = RestClient.builder();

		mockServer = MockRestServiceServer.bindTo(builder).build();

		detailsService = new DetailsServiceImpl(builder, "https://app.ticketmaster.com/discovery/v2",
				"uDn8Th1Hg0DWXaInqHaQ2gQ7s1VJ0Ru9");
	}
	
	@Test
	@DisplayName("Tests if service makes the correct call when introducing valid ID")
	void detailsOfConcert() {
		String idConcert = "Z698xZ2qZ1kJvuvbv";
		String urlEsperada = "https://app.ticketmaster.com/discovery/v2/events/"
							  + idConcert + ".json?apikey=uDn8Th1Hg0DWXaInqHaQ2gQ7s1VJ0Ru9";
		String result = """
				{
				  "id": "Z698xZ2qZ1kJvuvbv",
				  "name": "El Último de la Fila",
				  "url": "https://www.ticketmaster.es/event/el-ultimo-de-la-fila-entradas/981341601",
				  "dates": {
				    "start": {
				      "localDate": "2026-05-30"
				    }
				  },
				  "classifications": [
				    {
				      "genre": {
				        "name": "Rock"
				      }
				    }
				  ],
				  "_embedded": {
				    "venues": [
				      {
				        "name": "Bizkaia Arena - BEC!",
				        "postalCode": "null",
				        "location": {
				          "longitude": "-2.98869",
				          "latitude": "43.29087"
				        },
				        "address": {
				          "line1": "Rda. de Azkue, 1"
				        },
				        "city": {
				          "name": "Barakaldo"
				        },
				        "state": {
				          "name": "Biscay"
				        },
				        "country": {
				          "name": "Spain"
				        }
				      }
				    ],
				    "attractions": [
				      {
				        "name": "El Último de la Fila",
				        "url": "https://www.ticketmaster.com/el-ultimo-de-la-fila-tickets/artist/3676592"
				      }
				    ]
				  }
				}
				""";
		mockServer.expect(MockRestRequestMatchers.requestTo(
				urlEsperada))
				.andRespond(MockRestResponseCreators.withSuccess(result, MediaType.APPLICATION_JSON));
		ConcertResponseDTO details = detailsService.detailsConcert(idConcert);
		ConcertResponseDTO expectedResult = new ConcertResponseDTO(
				"Z698xZ2qZ1kJvuvbv", 
				"El Último de la Fila",
				LocalDate.of(2026, 05, 30),
				"https://www.ticketmaster.es/event/el-ultimo-de-la-fila-entradas/981341601", 
				"El Último de la Fila", 
				"Rock",
				"https://www.ticketmaster.com/el-ultimo-de-la-fila-tickets/artist/3676592",
				new VenueDTO(
						"Bizkaia Arena - BEC!", 
						43.29087, 
						-2.98869,
						"Rda. de Azkue, 1, null, Barakaldo", 
						"Biscay", 
						"Spain"
				)
		);
		assertEquals(expectedResult, details);
		mockServer.verify();
		
	}
	@Test
	@DisplayName("Tests if service throws IllegalArgumentException when API returns empty body")
	void detailsOfConcert_NullEvent() {
		
		
		String idConcert = "ID_VACIO";
		String urlEsperada = "https://app.ticketmaster.com/discovery/v2/events/"
							  + idConcert + ".json?apikey=uDn8Th1Hg0DWXaInqHaQ2gQ7s1VJ0Ru9";


		mockServer.expect(MockRestRequestMatchers.requestTo(urlEsperada))
				.andRespond(MockRestResponseCreators.withNoContent());

	
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			detailsService.detailsConcert(idConcert);
		});


		assertEquals("Concierto no encontrado", exception.getMessage());
		
		mockServer.verify();
	}
}
