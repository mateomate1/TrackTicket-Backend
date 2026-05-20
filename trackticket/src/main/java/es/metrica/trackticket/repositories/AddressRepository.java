package es.metrica.trackticket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.metrica.trackticket.models.Address;


@Repository
public interface AddressRepository extends JpaRepository<Address, Long>{
	
}
