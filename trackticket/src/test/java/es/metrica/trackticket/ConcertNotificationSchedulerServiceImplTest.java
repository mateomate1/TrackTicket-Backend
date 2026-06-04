package es.metrica.trackticket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import es.metrica.trackticket.models.Concert;
import es.metrica.trackticket.models.Notification;
import es.metrica.trackticket.models.NotificationType;
import es.metrica.trackticket.models.User;
import es.metrica.trackticket.repositories.ConcertRepository;
import es.metrica.trackticket.repositories.NotificationRepository;
import es.metrica.trackticket.repositories.UserRepository;
import es.metrica.trackticket.services.ConcertNotificationSchedulerServiceImpl;

@ExtendWith(MockitoExtension.class)
class ConcertNotificationSchedulerServiceImplTest {
	@Mock
	private ConcertRepository concertRepository;

	@Mock
	private NotificationRepository notificationRepository;

	@Mock
	private UserRepository userRepository;
	
	@InjectMocks
	private ConcertNotificationSchedulerServiceImpl schedulerService;
	private User mockUser;
	private Concert mockConcert;
	
	@BeforeEach
	void setUp() {
		mockUser = new User("UsuarioTest", "test@test.com", "pass123");
		mockConcert = new Concert("example-123", "Gira de Prueba 2026", LocalDate.now(), "link", null);
	}
	
	@Test
	void checkUpcomingConcerts_ShouldDoNothing_WhenNoConcertsFound() {

		when(concertRepository.findByConcertDate(any(LocalDate.class))).thenReturn(Collections.emptyList());

		schedulerService.checkUpcomingConcerts();

		verify(notificationRepository, never()).save(any(Notification.class));
	}
	
	@Test
	void checkUpcomingConcerts_ShouldSaveNotification_WhenConcertIsOneMonthAway() {
		LocalDate inOneMonth = LocalDate.now().plusMonths(1);
		LocalDate inFiveDays = LocalDate.now().plusDays(5);

		when(concertRepository.findByConcertDate(inOneMonth)).thenReturn(List.of(mockConcert));
		when(concertRepository.findByConcertDate(inFiveDays)).thenReturn(Collections.emptyList());
		when(userRepository.findByFavouriteConcertsContains(mockConcert)).thenReturn(List.of(mockUser));

		schedulerService.checkUpcomingConcerts();

		ArgumentCaptor<Notification> notificationCaptor = ArgumentCaptor.forClass(Notification.class);
		verify(notificationRepository, times(1)).save(notificationCaptor.capture());

		Notification savedNotification = notificationCaptor.getValue();

		assertEquals("¡Prepárate! Solo falta 1 mes para tu concierto: Gira de Prueba 2024", savedNotification.getMessage());
		assertEquals(NotificationType.UPCOMING_CONCERT, savedNotification.getType());
		assertEquals(mockUser, savedNotification.getUser());
		assertFalse(savedNotification.isRead());
	}
	
	@Test
	void checkUpcomingConcerts_ShouldSaveNotification_WhenConcertIsFiveDaysAway() {
		LocalDate inOneMonth = LocalDate.now().plusMonths(1);
		LocalDate inFiveDays = LocalDate.now().plusDays(5);

		when(concertRepository.findByConcertDate(inOneMonth)).thenReturn(Collections.emptyList());
		when(concertRepository.findByConcertDate(inFiveDays)).thenReturn(List.of(mockConcert));
		when(userRepository.findByFavouriteConcertsContains(mockConcert)).thenReturn(List.of(mockUser));

		schedulerService.checkUpcomingConcerts();

		ArgumentCaptor<Notification> notificationCaptor = ArgumentCaptor.forClass(Notification.class);
		verify(notificationRepository, times(1)).save(notificationCaptor.capture());

		Notification savedNotification = notificationCaptor.getValue();

		assertEquals("¡Ya queda poco! Faltan 5 días para tu concierto: Gira de Prueba 2024", savedNotification.getMessage());
		assertEquals(NotificationType.UPCOMING_CONCERT, savedNotification.getType());
		assertEquals(mockUser, savedNotification.getUser());
		assertFalse(savedNotification.isRead());
	}
}
