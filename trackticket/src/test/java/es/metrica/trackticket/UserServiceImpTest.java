package es.metrica.trackticket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import es.metrica.trackticket.dto.LoginRequestDTO;
import es.metrica.trackticket.dto.RegisterRequestDTO;
import es.metrica.trackticket.dto.TokenRequestDTO;
import es.metrica.trackticket.models.User;
import es.metrica.trackticket.repositories.UserRepository;
import es.metrica.trackticket.services.EncryptionService;
import es.metrica.trackticket.services.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserServiceImpTest {

	@Mock private UserRepository userRepository;
	@Mock private BCryptPasswordEncoder passwordEncoder;
	@Mock private EncryptionService encryptionService;
	@InjectMocks private UserServiceImpl userService;
	
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
	void login_OK_tokenNuevo() throws Exception {
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
	void login_OK_tokenExistente() throws Exception {
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
	void login_usuarioNoEncontrado() throws Exception {
	    LoginRequestDTO dto = new LoginRequestDTO("userCifrado", "passCifrada");

	    when(encryptionService.decrypt("userCifrado")).thenReturn("usuario");
	    when(encryptionService.decrypt("passCifrada")).thenReturn("password123");
	    when(userRepository.findAll()).thenReturn(List.of());

	    assertThrows(IllegalArgumentException.class, () -> userService.login(dto));
	}

	@Test
	void login_passwordIncorrecta() throws Exception {
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
	void deleteAccount_tokenNoEncontrado() {
	    TokenRequestDTO dto = new TokenRequestDTO("tokenCifrado");

	    when(encryptionService.decrypt("tokenCifrado")).thenReturn("tokenReal");
	    when(userRepository.findByUserSession("tokenReal")).thenReturn(Optional.empty());

	    assertThrows(IllegalArgumentException.class, () -> userService.deleteAccount(dto));
	}
}
