package es.metrica.trackticket.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.metrica.trackticket.models.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long>{
	
	List<City> findByCityName(String name);
	
	List<City> findByState_IdState(Long idState);
	
}
