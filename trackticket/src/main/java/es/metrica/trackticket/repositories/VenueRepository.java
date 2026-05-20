package es.metrica.trackticket.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.metrica.trackticket.models.Venue;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Long> {

	Optional<Venue> findByVenueName(String venueName);
	
	boolean existsByVenueName(String venueName);
	
	
	
}
