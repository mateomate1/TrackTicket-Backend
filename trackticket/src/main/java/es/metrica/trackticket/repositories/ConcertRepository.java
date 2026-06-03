	package es.metrica.trackticket.repositories;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.metrica.trackticket.models.Concert;

@Repository
public interface ConcertRepository extends JpaRepository<Concert, Long>{

	Optional<Concert> findByExternalIdConcert(String externalIdConcert);
	
	List<Concert> findByConcertDate(LocalDateTime concertDate);
	
	List<Concert> findByVenue_IdVenue(Long idVenue);
	
	List<Concert> findByArtists_IdArtist(Long artists);
	
	List<Concert> findByUsers_IdUser(Long idUser);
	
	List<Concert> findByConcertDate(LocalDate concertDate);
}
