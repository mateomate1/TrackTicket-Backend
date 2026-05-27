package es.metrica.trackticket.services;

import es.metrica.trackticket.dto.LoginRequestDTO;
import es.metrica.trackticket.dto.RegisterRequestDTO;
import es.metrica.trackticket.dto.TokenRequestDTO;

public interface UserService {

	public void register(RegisterRequestDTO dto);
	
	public void deleteAccount(TokenRequestDTO dto);
	
	public String login(LoginRequestDTO dto);
	
	public void logOut(TokenRequestDTO dto);
	
	
}
