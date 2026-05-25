package es.metrica.trackticket.services;

import java.util.List;

import es.metrica.trackticket.dto.ConcertResponseDTO;
import es.metrica.trackticket.dto.ConcertSearchRequestDTO;

public interface DetailsService {
	
	List<ConcertResponseDTO> detailsConcert(ConcertSearchRequestDTO dto);
}
