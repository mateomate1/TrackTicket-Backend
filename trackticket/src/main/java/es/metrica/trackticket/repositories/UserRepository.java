package es.metrica.trackticket.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.metrica.trackticket.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUserName(String username);
	
	Optional<User> findByEmail(String email);
	
	Optional<User> findByUserNameOrEmail(String userName, String email);

	Optional<User> findByUserSession(String token);
	
	boolean existsByEmail(String email);
	
	boolean existsByUserName(String email);
	
}
