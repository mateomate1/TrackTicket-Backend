package es.metrica.trackticket.services;

import java.util.List;

import org.springframework.stereotype.Service;

import es.metrica.trackticket.dto.CountResponseDTO;
import es.metrica.trackticket.dto.NotificationRequestDTO;
import es.metrica.trackticket.dto.NotificationResponseDTO;
import es.metrica.trackticket.dto.TokenRequestDTO;
import es.metrica.trackticket.dto.mapper.NotificationMapper;
import es.metrica.trackticket.exception.ResourceNotFoundException;
import es.metrica.trackticket.models.Notification;
import es.metrica.trackticket.repositories.NotificationRepository;

@Service
public class NotificationServiceImpl implements NotificationService {

	private NotificationRepository notificationRepository;

	public NotificationServiceImpl(NotificationRepository notificationRepository) {
		this.notificationRepository = notificationRepository;
	}

	@Override
	public List<NotificationResponseDTO> getNotifications(TokenRequestDTO dto) {

		List<Notification> notifications = this.notificationRepository.findByUser_UserSession(dto.token());

		return notifications.stream().map(NotificationMapper::mapToNotificationResponseDTO).toList();
	}

	@Override
	public CountResponseDTO countUnreadNotifications(TokenRequestDTO dto) {

		return new CountResponseDTO(this.notificationRepository.countByUser_UserSessionAndIsReadFalse(dto.token()));
	}

	@Override
	public void readNotification(NotificationRequestDTO dto) {

		Notification notification = this.notificationRepository.findById(dto.idNotification())
				.orElseThrow(() -> new ResourceNotFoundException("Notification not found"));

		notification.setRead(true);

		this.notificationRepository.save(notification);

	}

	@Override
	public void removeNotification(NotificationRequestDTO dto) {
		
		Notification notification = this.notificationRepository.findById(dto.idNotification())
				.orElseThrow(() -> new ResourceNotFoundException("Notification not found"));
		
		this.notificationRepository.delete(notification);
	}

}
