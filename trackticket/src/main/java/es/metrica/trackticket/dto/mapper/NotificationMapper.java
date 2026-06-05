package es.metrica.trackticket.dto.mapper;

import es.metrica.trackticket.dto.NotificationResponseDTO;
import es.metrica.trackticket.models.Notification;

public class NotificationMapper {

	public static NotificationResponseDTO mapToNotificationResponseDTO(Notification notification) {

		return new NotificationResponseDTO(notification.getIdNotification(), notification.getMessage(),
				String.valueOf(notification.getType()), notification.isRead(), notification.getNotificationTimestamp());
	}
}
