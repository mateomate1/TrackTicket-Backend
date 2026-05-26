package es.metrica.trackticket.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import es.metrica.trackticket.dto.RegisterRequestDTO;
import es.metrica.trackticket.models.User;
import es.metrica.trackticket.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	
	public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
		
	}
	
	@Override
	public void register(RegisterRequestDTO dto) {
		
		if(userRepository.existsByUserName(dto.name()) ) {
			throw new IllegalArgumentException("El nombre de usuario ya está registrado");
		}
		
		if(userRepository.existsByEmail(dto.email()) ) {
			throw new IllegalArgumentException("El email del usuario ya está registrado");
		
		}
		
		userRepository.save(
				new User( dto.name(), 
						  dto.email(), 
						  passwordEncoder.encode(dto.password())
						)
				);
	}

	
}
