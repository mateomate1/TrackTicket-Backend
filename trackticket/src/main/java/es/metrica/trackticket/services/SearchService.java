package es.metrica.trackticket.services;

import java.util.List;

import es.metrica.trackticket.dto.ConcertResponseDTO;
import es.metrica.trackticket.dto.ConcertSearchRequestDTO;

public interface SearchService {
	
	List<ConcertResponseDTO> searchConcerts(ConcertSearchRequestDTO dto);

}
