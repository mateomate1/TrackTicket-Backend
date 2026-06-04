package es.metrica.trackticket.services;

import java.util.List;

import es.metrica.trackticket.dto.CountResponseDTO;
import es.metrica.trackticket.dto.NotificationRequestDTO;
import es.metrica.trackticket.dto.NotificationResponseDTO;
import es.metrica.trackticket.dto.TokenRequestDTO;

public interface NotificationService {
	
	List<NotificationResponseDTO> getNotifications(TokenRequestDTO dto);
	
	CountResponseDTO countUnreadNotifications(TokenRequestDTO dto);
	
	void readNotification(NotificationRequestDTO dto);
	
	void removeNotification(NotificationRequestDTO dto);

}
