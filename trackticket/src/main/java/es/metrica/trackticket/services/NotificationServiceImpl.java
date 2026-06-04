package es.metrica.trackticket.services;

import java.util.List;

import es.metrica.trackticket.dto.CountResponseDTO;
import es.metrica.trackticket.dto.NotificationRequestDTO;
import es.metrica.trackticket.dto.NotificationResponseDTO;
import es.metrica.trackticket.dto.TokenRequestDTO;

public class NotificationServiceImpl implements NotificationService {

	@Override
	public List<NotificationResponseDTO> getNotifications(TokenRequestDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CountResponseDTO countUnreadNotifications(TokenRequestDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void readNotification(NotificationRequestDTO dto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeNotification(NotificationRequestDTO dto) {
		// TODO Auto-generated method stub
		
	}
	
	/*
	 * Métodos para los endpoints (listar notificaciones, contar las no leídas, marcar como leída y eliminar)
	 */

}
