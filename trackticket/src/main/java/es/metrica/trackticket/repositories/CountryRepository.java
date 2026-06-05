package es.metrica.trackticket.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.metrica.trackticket.models.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long>{

	Optional<Country> findByCountryName(String countryName);
	
}
