package es.metrica.trackticket.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

	@GetMapping()
	public ResponseEntity<List<Void>> getNotifications(@RequestHeader("user") String user) {
		
	}

	@PatchMapping("/{idNotification}")
	public ResponseEntity<Void> readNotification(@RequestHeader("user") String user, @PathVariable Long id) {

	}

	@DeleteMapping("/{idNotification}")
	public ResponseEntity<Void> deleteNotification(@RequestHeader("user") String user, @PathVariable Long id) {

	}
	
	@GetMapping("/countunread")
	public ResponseEntity<Integer> countUnread(@RequestHeader("user") String user) {
		
	}
	
	/*
	 * @GetMapping
	 * public ResponseEntity<List<NotificationResponseDTO>> getNotifications(@RequestHeader("Authorization") String token) {
	 * return null;
	 * }
	 * 
	 * @PatchMapping("/{idNotification}")
	 * public ResponseEntity<Void> readNotification(@RequestHeader("Authorization") String token, @PathVariable("idNotification") Long id) {
	 * return null;
	 * }
	 * 
	 * @DeleteMapping("/{idNotification}")
	 * public ResponseEntity<Void> deleteNotification(@RequestHeader("Authorization") String token, @PathVariable("idNotification") Long id) {
	 * 
	 * }
	 * 
	 * @GetMapping("/countunread")
	 * public ResponseEntity<Integer> countUnread(@RequestHeader("Authorization") String token) {
	 * 	
	 * }
	 */
}
