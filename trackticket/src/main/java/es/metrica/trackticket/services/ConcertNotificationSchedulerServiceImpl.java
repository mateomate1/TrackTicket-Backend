	package es.metrica.trackticket.services;
	
	import java.time.LocalDate;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import es.metrica.trackticket.models.Concert;
import es.metrica.trackticket.models.Notification;
import es.metrica.trackticket.models.NotificationType;
import es.metrica.trackticket.models.User;
import es.metrica.trackticket.repositories.ConcertRepository;
import es.metrica.trackticket.repositories.NotificationRepository;
import es.metrica.trackticket.repositories.UserRepository;
	
	@Service
	public class ConcertNotificationSchedulerServiceImpl implements ConcertNotificationSchedulerService{
	
		private final ConcertRepository concertRepository;
		private final NotificationRepository notificationRepository;
		private final UserRepository userRepository;
	
		
		
		public ConcertNotificationSchedulerServiceImpl(ConcertRepository concertRepository, 
				NotificationRepository notificationRepository, UserRepository userRepository) {
			this.concertRepository = concertRepository;
			this.notificationRepository = notificationRepository;
			this.userRepository = userRepository;
		}
	
		@Override
		@Scheduled(cron = "0 0 5 * * ?")
		public void checkUpcomingConcerts() {
			LocalDate today = LocalDate.now();
			LocalDate inOneMonth = today.plusMonths(1);	
			LocalDate inFiveDays = today.plusDays(5);
			notifyUsersForDate(inOneMonth, "¡Prepárate! Solo falta 1 mes");
			notifyUsersForDate(inFiveDays, "¡Ya queda poco! Faltan 5 días");
			
		}
		
		private void notifyUsersForDate(LocalDate targetDate, String messageTitle) {
			List<Concert> concerts = concertRepository.findByConcertDate(targetDate);

			if (concerts.isEmpty()) {
				return; 
			}

			for (Concert concert : concerts) {
				
				List<User> usersWithFavouriteConcert = userRepository.findByFavouriteConcertsContains(concert);
				
				for (User user : usersWithFavouriteConcert) {
					String fullMessage = messageTitle + " para tu concierto de " + concert.getConcertName();

					Notification notification = new Notification(
							null, 
							false, 
							fullMessage, 
							NotificationType.UPCOMING_CONCERT, 
							user
					);

					notificationRepository.save(notification);
				}
			}
		}
	
	
	}
