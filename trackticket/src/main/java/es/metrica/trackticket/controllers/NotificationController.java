package es.metrica.trackticket.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.metrica.trackticket.dto.CountResponseDTO;
import es.metrica.trackticket.dto.NotificationRequestDTO;
import es.metrica.trackticket.dto.NotificationResponseDTO;
import es.metrica.trackticket.dto.TokenRequestDTO;
import es.metrica.trackticket.services.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/v1/notifications")
@Tag(name = "Notifications", description = "Endpoints para gestión de notificaciones")
public class NotificationController {

	private final NotificationService notificationService;

	public NotificationController(NotificationService notificationService) {
		this.notificationService = notificationService;
	}

	@PostMapping("/list")
	@Operation(summary = "Listar notificaciones")
	public ResponseEntity<List<NotificationResponseDTO>> getNotifications(@RequestBody TokenRequestDTO dto) {
		return ResponseEntity.ok(notificationService.getNotifications(dto));
	}

	@PostMapping("/unread-count")
	@Operation(summary = "Contar notificaciones no leídas")
	public ResponseEntity<CountResponseDTO> countUnread(@RequestBody TokenRequestDTO dto) {
		return ResponseEntity.ok(notificationService.countUnreadNotifications(dto));
	}

	@PostMapping("/read")
	@Operation(summary = "Marcar notificación como leída")
	@ApiResponse(responseCode = "204", description = "Notificación marcada como leída")
	public ResponseEntity<Void> readNotification(@RequestBody NotificationRequestDTO dto) {
		notificationService.readNotification(dto);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/delete")
	@Operation(summary = "Eliminar una notificación")
	@ApiResponse(responseCode = "204", description = "Notificación eliminada")
	public ResponseEntity<Void> deleteNotification(@RequestBody NotificationRequestDTO dto) {
		notificationService.removeNotification(dto);
		return ResponseEntity.noContent().build();
	}
}
