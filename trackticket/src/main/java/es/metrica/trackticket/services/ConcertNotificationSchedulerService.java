package es.metrica.trackticket.services;

import java.util.List;

import es.metrica.trackticket.dto.CountResponseDTO;
import es.metrica.trackticket.dto.NotificationRequestDTO;
import es.metrica.trackticket.dto.NotificationResponseDTO;
import es.metrica.trackticket.dto.TokenRequestDTO;

public interface ConcertNotificationSchedulerService {

	void checkUpcomingConcerts();

}
