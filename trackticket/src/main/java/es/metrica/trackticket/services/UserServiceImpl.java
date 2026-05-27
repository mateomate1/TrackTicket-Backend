package es.metrica.trackticket.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import es.metrica.trackticket.dto.LoginRequestDTO;
import es.metrica.trackticket.dto.RegisterRequestDTO;
import es.metrica.trackticket.dto.TokenRequestDTO;
import es.metrica.trackticket.models.User;
import es.metrica.trackticket.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	private final EncryptionService encryptionService;
	
	public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, EncryptionService encryptionService) {
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
		this.encryptionService = encryptionService;
	}
	
	@Override
	public void register(RegisterRequestDTO dto) {
		
		String name = encryptionService.decrypt(dto.name());
		String email = encryptionService.decrypt(dto.email());
		String password = encryptionService.decrypt(dto.password());
		
		if(userRepository.existsByUserName(name) ) {
			throw new IllegalArgumentException("El nombre de usuario ya está registrado");
		}
		
		if(userRepository.existsByEmail(email) ) {
			throw new IllegalArgumentException("El email del usuario ya está registrado");
		
		}
		
		userRepository.save(
				new User( name, 
						  email, 
						  passwordEncoder.encode(password)
						)
				);
	}
	
	@Override
	public String login(LoginRequestDTO dto) {
		
		String user = encryptionService.decrypt(dto.user());
		String password = encryptionService.decrypt(dto.password());
		
		Optional<User> searchedUser = userRepository.findByUserNameOrEmail(user, user);
		
		if(searchedUser.isEmpty()) throw new IllegalArgumentException();
		else {
			
			if(passwordEncoder.matches(password, searchedUser.get().getPassword())) {
				var token = UUID.randomUUID().toString();
				
				searchedUser.get().setUserSession(token);
				userRepository.save(searchedUser.get());
				
				return token;
				
			} else throw new IllegalArgumentException();
			
		}
	}
	
	public void logOut(TokenRequestDTO dto) {
		
		String token = encryptionService.decrypt(dto.token());
		
		Optional<User> searchedUser = userRepository.findByUserSession(token);
		
		if(searchedUser.isEmpty()) throw new IllegalArgumentException();
		else {
			
			searchedUser.get().setUserSession(null);
			userRepository.save(searchedUser.get());
		
		}
		
	}
	
	public void deleteAccount(TokenRequestDTO dto) {
		
		String token = encryptionService.decrypt(dto.token());
		
		Optional<User> searchedUser = userRepository.findByUserSession(token);
		
		if(searchedUser.isEmpty()) throw new IllegalArgumentException();
		else {
			
			userRepository.delete(searchedUser.get());
		
		}
	}

	
}
