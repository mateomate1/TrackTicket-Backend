package es.metrica.trackticket.services;

import es.metrica.trackticket.dto.LoginRequestDTO;
import es.metrica.trackticket.dto.RegisterRequestDTO;
import es.metrica.trackticket.dto.TokenRequestDTO;
import es.metrica.trackticket.models.Artist;

public interface UserService {

	void register(RegisterRequestDTO dto);

	void deleteAccount(TokenRequestDTO dto);

	String login(LoginRequestDTO dto);

	void logOut(TokenRequestDTO dto);

	void addFavouriteArtist(String token, Artist artist);
}
