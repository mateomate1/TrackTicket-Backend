package es.metrica.trackticket.services;

import es.metrica.trackticket.dto.ConcertDetailsResponseDTO;


public interface DetailsService {
	
	ConcertDetailsResponseDTO detailsConcert(String idConcertTicketMaster);
}
