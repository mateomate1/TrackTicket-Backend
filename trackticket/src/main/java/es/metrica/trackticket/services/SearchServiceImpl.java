package es.metrica.trackticket.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import es.metrica.trackticket.dto.ConcertResponseDTO;
import es.metrica.trackticket.dto.ConcertSearchRequestDTO;
import es.metrica.trackticket.dto.VenueDTO;
import es.metrica.trackticket.dto.mapper.ConcertSearchMapper;
import es.metrica.trackticket.dto.mapper.ConcertSearchMapper.TicketMasterEvent;
import es.metrica.trackticket.dto.mapper.ConcertSearchMapper.TicketMasterResponse;
import es.metrica.trackticket.exception.ResourceNotFoundException;

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
				return response._embedded().events().stream().map(ConcertSearchMapper::mapToConcertResponseDTO).toList();
			} catch (NullPointerException e) {
				throw new ResourceNotFoundException("Búsqueda sin resultados");
			}
		} else {
			throw new ResourceNotFoundException("Búsqueda sin resultados");
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

	

	

	

}
