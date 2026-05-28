package es.metrica.trackticket.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.metrica.trackticket.dto.LoginRequestDTO;
import es.metrica.trackticket.dto.RegisterRequestDTO;
import es.metrica.trackticket.dto.TokenRequestDTO;
import es.metrica.trackticket.exception.NotLoggedInException;
import es.metrica.trackticket.models.Artist;
import es.metrica.trackticket.models.User;
import es.metrica.trackticket.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	private final EncryptionService encryptionService;

	public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder,
			EncryptionService encryptionService) {
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
		this.encryptionService = encryptionService;
	}

	@Override
	public void register(RegisterRequestDTO dto) {

		String name = encryptionService.decrypt(dto.name());
		String email = encryptionService.decrypt(dto.email());
		String password = encryptionService.decrypt(dto.password());

		boolean existsName = userRepository.findAll().stream()
				.anyMatch(n -> passwordEncoder.matches(name, n.getUserName()));

		boolean existsEmail = userRepository.findAll().stream()
				.anyMatch(n -> passwordEncoder.matches(email, n.getEmail()));

		if (existsName)
			throw new IllegalArgumentException("El nombre de usuario ya está registrado");
		if (existsEmail)
			throw new IllegalArgumentException("El email del usuario ya está registrado");

		userRepository.save(new User(passwordEncoder.encode(name), passwordEncoder.encode(email),
				passwordEncoder.encode(password)));
	}

	@Override
	public String login(LoginRequestDTO dto) {

		String user = encryptionService.decrypt(dto.user());
		String password = encryptionService.decrypt(dto.password());

		User searchedUser = userRepository.findAll().stream().filter(
				n -> passwordEncoder.matches(user, n.getUserName()) || passwordEncoder.matches(user, n.getEmail()))
				.findFirst().orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

		if (passwordEncoder.matches(password, searchedUser.getPassword())) {

			if (searchedUser.getUserSession() == null) {

				var token = UUID.randomUUID().toString();

				searchedUser.setUserSession(token);
				userRepository.save(searchedUser);

				return token;

			} else
				return searchedUser.getUserSession();

		} else
			throw new IllegalArgumentException();

	}

	public void logOut(TokenRequestDTO dto) {

		String token = encryptionService.decrypt(dto.token());

		Optional<User> searchedUser = userRepository.findByUserSession(token);

		if (searchedUser.isEmpty())
			throw new IllegalArgumentException();
		else {

			searchedUser.get().setUserSession(null);
			userRepository.save(searchedUser.get());

		}

	}

	public void deleteAccount(TokenRequestDTO dto) {

		String token = encryptionService.decrypt(dto.token());

		Optional<User> searchedUser = userRepository.findByUserSession(token);

		if (searchedUser.isEmpty())
			throw new IllegalArgumentException();
		else {

			userRepository.delete(searchedUser.get());

		}
	}

	@Transactional
	@Override
	public void addFavouriteArtist(String token, Artist artist) {
		User user = userRepository.findByUserSession(token)
				.orElseThrow(() -> new NotLoggedInException("Not a valid token"));

		if (!user.getFavouriteArtists().contains(artist)) {
			user.getFavouriteArtists().add(artist);
			userRepository.save(user);
		}
	}
}
