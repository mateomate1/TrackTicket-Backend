package es.metrica.trackticket.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface AddressRepository extends JpaRepository<Address, >{

}
