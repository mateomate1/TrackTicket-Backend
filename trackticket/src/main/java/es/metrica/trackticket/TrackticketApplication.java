package es.metrica.trackticket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TrackticketApplication {

	public static void main(String[] args) {
        SpringApplication.run(TrackticketApplication.class, args);
	}

}
