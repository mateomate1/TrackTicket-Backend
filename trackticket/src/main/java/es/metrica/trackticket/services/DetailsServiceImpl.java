package es.metrica.trackticket.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import es.metrica.trackticket.dto.ConcertResponseDTO;
import es.metrica.trackticket.dto.VenueDTO;
import es.metrica.trackticket.dto.mapper.ConcertSearchMapper;
import es.metrica.trackticket.dto.mapper.ConcertSearchMapper.TicketMasterEvent;

@Service
public class DetailsServiceImpl implements DetailsService{

	private RestClient restClient;
    private String apiKey;

 
    public DetailsServiceImpl(RestClient.Builder restClientBuilder,
            @Value("${ticketmaster.api.url}") String url,
            @Value("${ticketmaster.api.key}") String apiKey) {
        this.apiKey = apiKey;
        this.restClient = restClientBuilder.baseUrl(url).build();
    }
    
    @Override
	public ConcertResponseDTO detailsConcert(String idConcertTicketMaster) {
    	TicketMasterEvent event = restClient.get()
                .uri(uriBuilder -> uriBuilder
                    .path("/events/{id}.json")
                    .queryParam("apikey", this.apiKey)
                    .build(idConcertTicketMaster)) 
                .retrieve()
                .body(TicketMasterEvent.class);

            if (event == null) {
                throw new IllegalArgumentException("Concierto no encontrado");
            }
		return ConcertSearchMapper.mapToConcertResponseDTO(event);
	}
    
	

	
}



