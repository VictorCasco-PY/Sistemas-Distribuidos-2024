package com.api.apireservas.controller;

import com.api.apireservas.dto.PageResponse;
import com.api.apireservas.dto.ReservaDto;
import com.api.apireservas.service.reserva.ReservaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservas")
public class ReservaDetalleController {

    private static final Logger logger = LoggerFactory.getLogger(ReservaDetalleController.class);

    @Autowired
    private ReservaService reservaService;

    // Crear una nueva reserva con detalles
    @PostMapping
    public ResponseEntity<ReservaDto> crearReserva(@RequestBody ReservaDto reservaDto) {
        logger.info("Inicio de la creación de reserva para el cliente con ID: {}", reservaDto.getClienteId());

        // Crear la reserva y los detalles asociados
        ReservaDto nuevaReserva = reservaService.create(reservaDto);

        logger.info("Reserva creada con éxito con ID: {}", nuevaReserva.getId());
        return ResponseEntity.ok(nuevaReserva);
    }

    // Obtener una reserva por su ID
    @GetMapping("/{id}")
    public ResponseEntity<ReservaDto> obtenerReserva(@PathVariable Long id) {
        logger.info("Solicitud de obtención de reserva con ID: {}", id);

        // Obtener la reserva por su ID
        ReservaDto reserva = reservaService.getById(id);

        logger.info("Reserva obtenida con éxito con ID: {}", id);
        return ResponseEntity.ok(reserva);
    }

    // Obtener todas las reservas (paginadas)
    @GetMapping("/page/{pageNum}")
    public ResponseEntity<PageResponse<ReservaDto>> getAllReservas(@PathVariable int pageNum) {
        logger.info("Solicitud para obtener todas las reservas en la página: {}", pageNum);

        // Llamar al servicio para obtener las reservas paginadas
        PageResponse<ReservaDto> reservas = reservaService.getAll(pageNum);

        logger.info("Reservas obtenidas con éxito para la página: {}", pageNum);
        return ResponseEntity.ok(reservas);
    }

    // Actualizar una reserva existente
    @PutMapping("/{id}")
    public ResponseEntity<ReservaDto> actualizarReserva(@PathVariable Long id, @RequestBody ReservaDto reservaDto) {
        logger.info("Inicio de la actualización de la reserva con ID: {}", id);

        // Actualizar la reserva existente
        ReservaDto reservaActualizada = reservaService.update(id, reservaDto);

        logger.info("Reserva con ID: {} actualizada con éxito", id);
        return ResponseEntity.ok(reservaActualizada);
    }

    // Eliminar una reserva por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReserva(@PathVariable Long id) {
        logger.info("Solicitud de eliminación de la reserva con ID: {}", id);

        // Eliminar la reserva por su ID
        reservaService.delete(id);

        logger.info("Reserva con ID: {} eliminada exitosamente", id);
        return ResponseEntity.noContent().build();
    }
}
