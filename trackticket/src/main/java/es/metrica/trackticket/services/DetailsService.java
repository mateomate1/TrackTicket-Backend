package es.metrica.trackticket.services;

import es.metrica.trackticket.dto.ConcertResponseDTO;


public interface DetailsService {
	
	ConcertResponseDTO detailsConcert(String idConcertTicketMaster);
}
