package es.metrica.trackticket.models;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table (name = "notifications")
public class Notification {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "id_notification")
	private Long idNotification;
	
	@Column (name = "is_read", nullable = false)
	private boolean isRead;
	
	@Column (name = "message", nullable = false)
	private String message;
	
	@Enumerated(EnumType.STRING)
	@Column (name = "type", nullable = false)
	private NotificationType type;
	
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn(name = "id_user", nullable = false)
	private User user;
	
	public Notification(Long idNotification, boolean isRead, String message, NotificationType type, User user) {
		this.idNotification = idNotification;
		this.isRead = isRead;
		this.message = message;
		this.type = type;
		this.user = user;
	}
	
	protected Notification() {}

	public Long getIdNotification() {
		return idNotification;
	}

	public void setIdNotification(Long idNotification) {
		this.idNotification = idNotification;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public NotificationType getType() {
		return type;
	}

	public void setType(NotificationType type) {
		this.type = type;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	

	
}
