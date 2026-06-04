package es.metrica.trackticket.services;

import java.util.List;

import es.metrica.trackticket.dto.CountResponseDTO;
import es.metrica.trackticket.dto.NotificationRequestDTO;
import es.metrica.trackticket.dto.NotificationResponseDTO;
import es.metrica.trackticket.dto.TokenRequestDTO;
import es.metrica.trackticket.dto.mapper.NotificationMapper;
import es.metrica.trackticket.models.Notification;
import es.metrica.trackticket.repositories.NotificationRepository;


public class NotificationServiceImpl implements NotificationService {


	private NotificationRepository notificationRepostory;

	public NotificationServiceImpl(NotificationRepository notificationRepository) {
		this.notificationRepostory = notificationRepository;
	}

	@Override
	public List<NotificationResponseDTO> getNotifications(TokenRequestDTO dto) {
		
		List<Notification> notifications = this.notificationRepostory.findByUser_UserSession(dto.token());
		
		return notifications.stream().map(NotificationMapper::mapToNotificationResponseDTO).toList();
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



}
