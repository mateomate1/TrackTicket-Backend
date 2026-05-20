package es.metrica.trackticket.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.metrica.trackticket.models.Address;


@Repository
public interface AddressRepository extends JpaRepository<Address, Long>{
	
	Optional<Address> findByFirstLine(String firstLine);
	Optional<Address> findByFirstLineAndSecondLine(String firstLine, String secondLine);
	
	boolean existsByFirstLine(String firstLine);
	boolean existsByFirstLineAndSecondLine(String firstLine, String secondLine);
	
	Optional<Address> findByZipCode(String zipCode);
	
	List<Address> findByCity_IdCity(Long idCity);
	
}
