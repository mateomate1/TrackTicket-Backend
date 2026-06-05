package es.metrica.trackticket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.metrica.trackticket.models.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long>{
	// No es necesario funcionalidades..
	
}
