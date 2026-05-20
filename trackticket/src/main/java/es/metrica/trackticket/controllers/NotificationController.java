package es.metrica.trackticket.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.metrica.trackticket.dto.CountResponseDTO;
import es.metrica.trackticket.dto.NotificationRequestDTO;
import es.metrica.trackticket.dto.NotificationResponseDTO;
import es.metrica.trackticket.dto.TokenRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/notifications")
@Tag(name = "Notifications", description = "Endpoints para gestión de notificaciones")
public class NotificationController {
	
	@PostMapping("/list")
	@Operation(summary = "Listar notificaciones")
	public ResponseEntity<List<NotificationResponseDTO>> getNotifications(@RequestBody TokenRequestDTO dto) {
		return ResponseEntity.ok(List.of());
	}
	
	@PostMapping("/unread-count")
	@Operation(summary = "Contar notificaciones no leídas")
	public ResponseEntity<CountResponseDTO> countUnread(@RequestBody TokenRequestDTO dto) {
		return ResponseEntity.ok(new CountResponseDTO());
	}
	
	@PostMapping("/read")
	@Operation(summary = "Marcar notificación como leída")
	@ApiResponse(responseCode = "204", description = "Notificación marcada como leída")
	public ResponseEntity<Void> readNotification(@RequestBody NotificationRequestDTO dto) {
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping("/delete")
	@Operation(summary = "Eliminar una notificación")
	@ApiResponse(responseCode = "204", description = "Notificación eliminada")
	public ResponseEntity<Void> deleteNotification(@RequestBody NotificationRequestDTO dto) {
		return ResponseEntity.noContent().build();
	}
}
