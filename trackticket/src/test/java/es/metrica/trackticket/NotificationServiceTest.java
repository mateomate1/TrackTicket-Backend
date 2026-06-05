package es.metrica.trackticket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import es.metrica.trackticket.dto.CountResponseDTO;
import es.metrica.trackticket.dto.NotificationRequestDTO;
import es.metrica.trackticket.dto.NotificationResponseDTO;
import es.metrica.trackticket.dto.TokenRequestDTO;
import es.metrica.trackticket.exception.NotLoggedInException;
import es.metrica.trackticket.exception.ResourceNotFoundException;
import es.metrica.trackticket.models.Notification;
import es.metrica.trackticket.models.NotificationType;
import es.metrica.trackticket.models.User;
import es.metrica.trackticket.repositories.NotificationRepository;
import es.metrica.trackticket.repositories.UserRepository;
import es.metrica.trackticket.services.NotificationServiceImpl;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

	@Mock
	private NotificationRepository notificationRepository;
	@Mock
	private UserRepository userRepository;
	@Mock
	User user;
	private NotificationServiceImpl notificationService;

	@BeforeEach
	void setUp() {
		notificationService = new NotificationServiceImpl(notificationRepository, userRepository);
	}

	@Test
	@DisplayName("Tests if getNotifications returns the correct list of NotificationResponseDTO")
	void getNotificationsTest() {
		TokenRequestDTO dto = new TokenRequestDTO("1234");
		when(userRepository.findByUserSession(dto.token())).thenReturn(Optional.of(user));
		when(notificationRepository.findByUser(user)).thenReturn(List.of(
				new Notification("hola", NotificationType.CANCELLED_CONCERT, user,
						LocalDateTime.of(2026, 06, 05, 0, 0)),
				new Notification("adiós", NotificationType.NEW_CONCERT, user, LocalDateTime.of(2026, 06, 04, 0, 0))));

		List<NotificationResponseDTO> result = notificationService.getNotifications(dto);

		List<NotificationResponseDTO> shouldBe = List.of(
				new NotificationResponseDTO(null, "hola", String.valueOf(NotificationType.CANCELLED_CONCERT), false,
						LocalDateTime.of(2026, 06, 05, 0, 0)),
				new NotificationResponseDTO(null, "adiós", String.valueOf(NotificationType.NEW_CONCERT), false,
						LocalDateTime.of(2026, 06, 04, 0, 0)));

		assertEquals(2, result.size());
		assertEquals(shouldBe, result);
	}

	@Test
	@DisplayName("Tests if getNotification throws NotLoggedInException when the token is not valid")
	void getNotificationTestNotValidUser() {
		TokenRequestDTO dto = new TokenRequestDTO("1234");
		when(userRepository.findByUserSession(dto.token())).thenReturn(Optional.empty());

		Exception e = assertThrows(NotLoggedInException.class, () -> notificationService.getNotifications(dto));

		assertEquals("Not a valid user", e.getMessage());
	}

	@Test
	@DisplayName("Tests if countUnreadNotifications returns the correct amount of unread notifications")
	void countUnreadNotificationsTest() {
		TokenRequestDTO dto = new TokenRequestDTO("1234");
		when(userRepository.findByUserSession(dto.token())).thenReturn(Optional.of(user));
		when(notificationRepository.countByUserAndReadFalse(user)).thenReturn(10L);

		CountResponseDTO result = notificationService.countUnreadNotifications(dto);

		assertEquals(10, result.amount());
	}

	@Test
	@DisplayName("Tests if countUnreadNotifications throws NotLoggedInException when the token is not valid")
	void countUnreadNotificationsTestNotValidUser() {
		TokenRequestDTO dto = new TokenRequestDTO("1234");
		when(userRepository.findByUserSession(dto.token())).thenReturn(Optional.empty());

		Exception e = assertThrows(NotLoggedInException.class, () -> notificationService.countUnreadNotifications(dto));

		assertEquals("Not a valid user", e.getMessage());
	}

	@Test
	@DisplayName("Tests if readNotification changes isRead to true and saves the notification")
	void readNotificationTest() {
		Notification notification = new Notification("hola", NotificationType.UPCOMING_CONCERT, user,
				LocalDateTime.of(2026, 06, 04, 0, 0));

		when(notificationRepository.findById(1L)).thenReturn(Optional.of(notification));

		notificationService.readNotification(new NotificationRequestDTO("1234", 1L));

		assertTrue(notification.isRead());
		verify(notificationRepository, times(1)).save(any());
	}

	@Test
	@DisplayName("Tests if readNotification throws ResourceNotFoundException when the notification id is ont in the database")
	void readNotificationTestRNFE() {

		when(notificationRepository.findById(1L)).thenReturn(Optional.empty());

		Exception e = assertThrows(ResourceNotFoundException.class,
				() -> notificationService.readNotification(new NotificationRequestDTO("1234", 1L)));

		assertEquals("Notification not found", e.getMessage());

		verify(notificationRepository, never()).save(any());
	}

	@Test
	@DisplayName("Tests if readNotification deletes the notification")
	void removeNotificationTest() {
		Notification notification = new Notification("hola", NotificationType.UPCOMING_CONCERT, user,
				LocalDateTime.of(2026, 06, 04, 0, 0));

		when(notificationRepository.findById(1L)).thenReturn(Optional.of(notification));

		notificationService.removeNotification(new NotificationRequestDTO("1234", 1L));

		verify(notificationRepository, times(1)).delete(any());
	}

	@Test
	@DisplayName("Tests if removeNotification throws ResourceNotFoundException when the notification id is ont in the database")
	void removeNotificationTestRNFE() {

		when(notificationRepository.findById(1L)).thenReturn(Optional.empty());

		Exception e = assertThrows(ResourceNotFoundException.class,
				() -> notificationService.removeNotification(new NotificationRequestDTO("1234", 1L)));

		assertEquals("Notification not found", e.getMessage());

		verify(notificationRepository, never()).delete(any());
	}
}
