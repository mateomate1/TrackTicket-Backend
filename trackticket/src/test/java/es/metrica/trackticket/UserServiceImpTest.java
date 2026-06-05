package es.metrica.trackticket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import es.metrica.trackticket.dto.LoginRequestDTO;
import es.metrica.trackticket.dto.RegisterRequestDTO;
import es.metrica.trackticket.dto.TokenRequestDTO;
import es.metrica.trackticket.exception.NotLoggedInException;
import es.metrica.trackticket.models.Artist;
import es.metrica.trackticket.models.User;
import es.metrica.trackticket.repositories.UserRepository;
import es.metrica.trackticket.services.EncryptionService;
import es.metrica.trackticket.services.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserServiceImpTest {

	@Mock
	private UserRepository userRepository;
	@Mock
	private BCryptPasswordEncoder passwordEncoder;
	@Mock
	private EncryptionService encryptionService;
	@Mock
	private User user;
	@InjectMocks
	private UserServiceImpl userService;

	@Test
	void register_OK() {
		RegisterRequestDTO dto = new RegisterRequestDTO("usuarioCifrado", "emailCifrado", "passwordCifrada");

		when(encryptionService.decrypt("usuarioCifrado")).thenReturn("usuario");
		when(encryptionService.decrypt("emailCifrado")).thenReturn("email@test.com");
		when(encryptionService.decrypt("passwordCifrada")).thenReturn("password123");
		when(userRepository.findAll()).thenReturn(List.of());
		when(passwordEncoder.encode(any(String.class))).thenReturn("hashSimulado");

		userService.register(dto);

		verify(passwordEncoder).encode("password123");
	}

	@Test
	void register_userNameDuplicado() {
		RegisterRequestDTO dto = new RegisterRequestDTO("usuarioCifrado", "emailCifrado", "passwordCifrada");
		User existente = new User("usuario", "otro@test.com", "hash");

		when(encryptionService.decrypt("usuarioCifrado")).thenReturn("usuario");
		when(encryptionService.decrypt("emailCifrado")).thenReturn("email@test.com");
		when(encryptionService.decrypt("passwordCifrada")).thenReturn("password123");
		when(userRepository.findAll()).thenReturn(List.of(existente));
		when(passwordEncoder.matches("usuario", existente.getUserName())).thenReturn(true);

		assertThrows(IllegalArgumentException.class, () -> userService.register(dto));
	}

	@Test
	void register_emailDuplicado() {
		RegisterRequestDTO dto = new RegisterRequestDTO("usuarioCifrado", "emailCifrado", "passwordCifrada");
		User existente = new User("otro", "email@test.com", "hash");

		when(encryptionService.decrypt("usuarioCifrado")).thenReturn("usuario");
		when(encryptionService.decrypt("emailCifrado")).thenReturn("email@test.com");
		when(encryptionService.decrypt("passwordCifrada")).thenReturn("password123");
		when(userRepository.findAll()).thenReturn(List.of(existente));
		when(passwordEncoder.matches("usuario", existente.getUserName())).thenReturn(false);
		when(passwordEncoder.matches("email@test.com", existente.getEmail())).thenReturn(true);

		assertThrows(IllegalArgumentException.class, () -> userService.register(dto));
	}

	@Test
	void register_passwordHasheada() {
		RegisterRequestDTO dto = new RegisterRequestDTO("usuarioCifrado", "emailCifrado", "passwordCifrada");

		when(encryptionService.decrypt("usuarioCifrado")).thenReturn("usuario");
		when(encryptionService.decrypt("emailCifrado")).thenReturn("email@test.com");
		when(encryptionService.decrypt("passwordCifrada")).thenReturn("password123");
		when(userRepository.findAll()).thenReturn(List.of());
		when(passwordEncoder.encode(any(String.class))).thenReturn("hashSimulado");

		userService.register(dto);

		verify(passwordEncoder).encode("password123");
	}

	@Test
	void login_OK_tokenNuevo() {
		LoginRequestDTO dto = new LoginRequestDTO("userCifrado", "passCifrada");
		User usuario = new User("usuario", "email@test.com", "hashPassword");

		when(encryptionService.decrypt("userCifrado")).thenReturn("usuario");
		when(encryptionService.decrypt("passCifrada")).thenReturn("password123");
		when(userRepository.findAll()).thenReturn(List.of(usuario));
		when(passwordEncoder.matches("usuario", usuario.getUserName())).thenReturn(true);
		when(passwordEncoder.matches("password123", usuario.getPassword())).thenReturn(true);

		String token = userService.login(dto);

		assertNotNull(token);
		verify(userRepository).save(any(User.class));
	}

	@Test
	void login_OK_tokenExistente() {
		LoginRequestDTO dto = new LoginRequestDTO("userCifrado", "passCifrada");
		User usuario = new User("usuario", "email@test.com", "hashPassword");
		usuario.setUserSession("tokenExistente");

		when(encryptionService.decrypt("userCifrado")).thenReturn("usuario");
		when(encryptionService.decrypt("passCifrada")).thenReturn("password123");
		when(userRepository.findAll()).thenReturn(List.of(usuario));
		when(passwordEncoder.matches("usuario", usuario.getUserName())).thenReturn(true);
		when(passwordEncoder.matches("password123", usuario.getPassword())).thenReturn(true);

		String token = userService.login(dto);

		assertEquals("tokenExistente", token);
	}

	@Test
	void login_usuarioNoEncontrado() {
		LoginRequestDTO dto = new LoginRequestDTO("userCifrado", "passCifrada");

		when(encryptionService.decrypt("userCifrado")).thenReturn("usuario");
		when(encryptionService.decrypt("passCifrada")).thenReturn("password123");
		when(userRepository.findAll()).thenReturn(List.of());

		assertThrows(IllegalArgumentException.class, () -> userService.login(dto));
	}

	@Test
	void login_passwordIncorrecta() {
		LoginRequestDTO dto = new LoginRequestDTO("userCifrado", "passCifrada");
		User usuario = new User("usuario", "email@test.com", "hashPassword");

		when(encryptionService.decrypt("userCifrado")).thenReturn("usuario");
		when(encryptionService.decrypt("passCifrada")).thenReturn("password123");
		when(userRepository.findAll()).thenReturn(List.of(usuario));
		when(passwordEncoder.matches("usuario", usuario.getUserName())).thenReturn(true);
		when(passwordEncoder.matches("password123", usuario.getPassword())).thenReturn(false);

		assertThrows(IllegalArgumentException.class, () -> userService.login(dto));
	}

	@Test
	void logout_OK() {
		TokenRequestDTO dto = new TokenRequestDTO("tokenCifrado");
		User usuario = new User("usuario", "email@test.com", "hashPassword");
		usuario.setUserSession("tokenReal");

		when(encryptionService.decrypt("tokenCifrado")).thenReturn("tokenReal");
		when(userRepository.findByUserSession("tokenReal")).thenReturn(Optional.of(usuario));

		userService.logOut(dto);

		verify(userRepository).save(any(User.class));
	}

	@Test
	void logout_tokenNoEncontrado() {
		TokenRequestDTO dto = new TokenRequestDTO("tokenCifrado");

		when(encryptionService.decrypt("tokenCifrado")).thenReturn("tokenReal");
		when(userRepository.findByUserSession("tokenReal")).thenReturn(Optional.empty());

		assertThrows(IllegalArgumentException.class, () -> userService.logOut(dto));
	}

	@Test
	void deleteAccount_OK() {
		TokenRequestDTO dto = new TokenRequestDTO("tokenCifrado");
		User usuario = new User("usuario", "email@test.com", "hashPassword");

		when(encryptionService.decrypt("tokenCifrado")).thenReturn("tokenReal");
		when(userRepository.findByUserSession("tokenReal")).thenReturn(Optional.of(usuario));

		userService.deleteAccount(dto);

		verify(userRepository).delete(any(User.class));
	}

	@Test
	void deleteAccount_tokenNoEncontrado()  {
		TokenRequestDTO dto = new TokenRequestDTO("tokenCifrado");

		when(encryptionService.decrypt("tokenCifrado")).thenReturn("tokenReal");
		when(userRepository.findByUserSession("tokenReal")).thenReturn(Optional.empty());

		assertThrows(IllegalArgumentException.class, () -> userService.deleteAccount(dto));
	}
	
	@Test
	@DisplayName("Tests if addFavouriteArtist correctly saves a user's favourite artist")
	void addFavouriteArtistValid() {
		Artist artist = new Artist("externalId", "artistName");
		when(userRepository.findByUserSession("1234")).thenReturn(Optional.of(user));
		List<Artist> favourites = new ArrayList<>();
		when(user.getFavouriteArtists()).thenReturn(favourites);
		
		userService.addFavouriteArtist("1234", artist);
		
		verify(user, times(2)).getFavouriteArtists();
		verify(userRepository).save(user);
	}
	
	@Test
	@DisplayName("Tests if addFavouriteArtist doesn't save a user's favourite artist if it's already a favourite artist")
	void addAlreadyFavouriteArtist() {
		Artist artist = new Artist("externalId", "artistName");
		List<Artist> favourites = List.of(artist);
		when(userRepository.findByUserSession("1234")).thenReturn(Optional.of(user));
		when(user.getFavouriteArtists()).thenReturn(favourites);
		
		userService.addFavouriteArtist("1234", artist);
		
		verify(userRepository, never()).save(user);
	}
	
	@Test
	@DisplayName("Tests if addFavouriteArtist throws NotLoggedInException when the token is not valid")
	void addFavouriteArtistNoToken() {
		Artist artist = new Artist("externalId", "artistName");
		when(userRepository.findByUserSession("1234")).thenReturn(Optional.empty());
		
		Exception e = assertThrows(NotLoggedInException.class, () -> userService.addFavouriteArtist("1234", artist));
		
		assertEquals("Not a valid token", e.getMessage());
		verify(user, never()).getFavouriteArtists();
		verify(userRepository, never()).save(user);
	}
	
	@Test
	void registerWrongEmail() {
		RegisterRequestDTO dto = new RegisterRequestDTO("usuarioCifrado", "emailCifrado", "passwordCifrada");

		when(encryptionService.decrypt("usuarioCifrado")).thenReturn("usuario");
		when(encryptionService.decrypt("emailCifrado")).thenReturn("email@test");
		when(encryptionService.decrypt("passwordCifrada")).thenReturn("password123");

		Exception e = assertThrows(IllegalArgumentException.class, () -> userService.register(dto));

		assertEquals("El email debe tener formato válido", e.getMessage());
	}
	
	@Test
	void registerWrongPassword() {
		RegisterRequestDTO dto = new RegisterRequestDTO("usuarioCifrado", "emailCifrado", "passwordCifrada");

		when(encryptionService.decrypt("usuarioCifrado")).thenReturn("usuario");
		when(encryptionService.decrypt("emailCifrado")).thenReturn("email@test.com");
		when(encryptionService.decrypt("passwordCifrada")).thenReturn("pass12");

		Exception e = assertThrows(IllegalArgumentException.class, () -> userService.register(dto));

		assertEquals("La contraseña debe tener mínimo 8 caracteres", e.getMessage());
	}
	
}
