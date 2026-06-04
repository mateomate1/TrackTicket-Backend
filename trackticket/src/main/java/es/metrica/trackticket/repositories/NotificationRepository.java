package es.metrica.trackticket.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.metrica.trackticket.models.Notification;
import es.metrica.trackticket.models.NotificationType;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long>{

	List<Notification> findByIsRead(Boolean isRead);
	
	List<Notification> findByType(NotificationType type);
	
	List<Notification> findByUser_IdUser(Long idUser);
	
	List<Notification> findByUser_UserSession(String userSession);
	
	
	
}
