package es.metrica.trackticket.services;

import java.util.List;

import org.springframework.data.domain.Limit;
import org.springframework.stereotype.Service;

import es.metrica.trackticket.dto.CountResponseDTO;
import es.metrica.trackticket.dto.NotificationRequestDTO;
import es.metrica.trackticket.dto.NotificationResponseDTO;
import es.metrica.trackticket.dto.TokenRequestDTO;
import es.metrica.trackticket.dto.mapper.NotificationMapper;
import es.metrica.trackticket.exception.NotLoggedInException;
import es.metrica.trackticket.exception.ResourceNotFoundException;
import es.metrica.trackticket.models.Notification;
import es.metrica.trackticket.models.User;
import es.metrica.trackticket.repositories.NotificationRepository;
import es.metrica.trackticket.repositories.UserRepository;

@Service
public class NotificationServiceImpl implements NotificationService {

	private NotificationRepository notificationRepository;
	private UserRepository userRepository;

	public NotificationServiceImpl(NotificationRepository notificationRepository, UserRepository userRepository) {
		this.notificationRepository = notificationRepository;
		this.userRepository = userRepository;
	}

	@Override
	public List<NotificationResponseDTO> getNotifications(TokenRequestDTO dto) {

		User user = userRepository.findByUserSession(dto.token())
				.orElseThrow(() -> new NotLoggedInException("Not a valid user"));

		List<Notification> notifications = this.notificationRepository.findByUser(user);

		return notifications.stream().map(NotificationMapper::mapToNotificationResponseDTO).toList();
	}

	@Override
	public CountResponseDTO countUnreadNotifications(TokenRequestDTO dto) {
		
		User user = userRepository.findByUserSession(dto.token())
				.orElseThrow(() -> new NotLoggedInException("Not a valid user"));

		return new CountResponseDTO((int)(this.notificationRepository.countByUserAndReadFalse(user)));
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
