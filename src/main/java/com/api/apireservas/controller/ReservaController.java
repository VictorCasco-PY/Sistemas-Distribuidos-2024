package com.api.apireservas.controller;

import com.api.apireservas.dto.PageResponse;
import com.api.apireservas.dto.ReservaDto;
import com.api.apireservas.service.reserva.IReservaService;
import com.api.apireservas.utils.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {
    private static final Logger logger = LoggerFactory.getLogger(ReservaController.class);

    @Autowired
    private IReservaService reservaService;

    @PostMapping
    public ResponseEntity<ReservaDto> createReserva(@RequestBody ReservaDto reservaDto) {
        logger.info("Creando una nueva reserva: {}", reservaDto);
        ReservaDto createdReserva = reservaService.createReserva(reservaDto);
        return ResponseEntity.ok(createdReserva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservaDto> updateReserva(@PathVariable Long id, @RequestBody ReservaDto reservaDto) {
        logger.info("Actualizando reserva con id: {}", id);
        ReservaDto updatedReserva = reservaService.updateReserva(id, reservaDto);
        return ResponseEntity.ok(updatedReserva);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReserva(@PathVariable Long id) {
        logger.warn("Borrando reserva con id: {}", id);
        reservaService.deleteReserva(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaDto> getReservaById(@PathVariable Long id) {
        logger.info("Obteniendo reserva con id: {}", id);
        ReservaDto reserva = reservaService.getReservaById(id);
        return ResponseEntity.ok(reserva);
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<PageResponse<ReservaDto>> getAllReservas(
            @PathVariable("page") int page,
            @RequestParam(value = "size", defaultValue = "5") int size) {
        logger.info("Listando todas las reservas - Página: {}, Tamaño: {}", page, size);

        if (size <= 0) {
            size = Settings.PAGE_SIZE;
        }

        Pageable pageable = PageRequest.of(page - 1, size); // La paginación empieza en 0
        PageResponse<ReservaDto> reservas = reservaService.getAllReservas(pageable);
        return ResponseEntity.ok(reservas);
    }

    @GetMapping("/estado/{estado}/page/{page}")
    public ResponseEntity<PageResponse<ReservaDto>> getReservasByEstado(
            @PathVariable("estado") String estado,
            @PathVariable("page") int page,
            @RequestParam(value = "size", defaultValue = "5") int size) {
        logger.info("Listando reservas con estado: {} - Página: {}, Tamaño: {}", estado, page, size);

        if (size <= 0) {
            size = Settings.PAGE_SIZE;
        }

        Pageable pageable = PageRequest.of(page - 1, size); // La paginación empieza en 0
        PageResponse<ReservaDto> reservas = reservaService.getReservasByEstado(estado, pageable);
        return ResponseEntity.ok(reservas);
    }

}
