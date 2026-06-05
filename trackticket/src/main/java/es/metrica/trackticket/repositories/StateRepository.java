package es.metrica.trackticket.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.metrica.trackticket.models.State;

@Repository
public interface StateRepository extends JpaRepository<State, Long>{

	Optional<State> findByStateName(String stateName);
	
	List<State> findByCountry_IdCountry(Long idCountry);
	
}
