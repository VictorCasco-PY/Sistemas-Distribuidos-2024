package com.api.apireservas.controller;

import com.api.apireservas.dto.DetalleReservaDto;
import com.api.apireservas.dto.PageResponse;
import com.api.apireservas.service.reserva.IDetalleReservaService;
import com.api.apireservas.utils.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/detalles-reserva")
public class DetalleReservaController {

    private static final Logger logger = LoggerFactory.getLogger(DetalleReservaController.class);

    @Autowired
    private IDetalleReservaService detalleReservaService;

    @PostMapping
    public ResponseEntity<DetalleReservaDto> createDetalleReserva(@RequestBody DetalleReservaDto detalleReservaDto) {
        logger.info("Creando un nuevo detalle de reserva: {}", detalleReservaDto);
        DetalleReservaDto createdDetalle = detalleReservaService.createDetalleReserva(detalleReservaDto);
        return ResponseEntity.ok(createdDetalle);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalleReservaDto> updateDetalleReserva(@PathVariable Long id, @RequestBody DetalleReservaDto detalleReservaDto) {
        logger.info("Actualizando detalle de reserva con id: {}", id);
        DetalleReservaDto updatedDetalle = detalleReservaService.updateDetalleReserva(id, detalleReservaDto);
        return ResponseEntity.ok(updatedDetalle);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDetalleReserva(@PathVariable Long id) {
        logger.warn("Borrando detalle de reserva con id: {}", id);
        detalleReservaService.deleteDetalleReserva(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleReservaDto> getDetalleReservaById(@PathVariable Long id) {
        logger.info("Obteniendo detalle de reserva con id: {}", id);
        DetalleReservaDto detalleReserva = detalleReservaService.getDetalleReservaById(id);
        return ResponseEntity.ok(detalleReserva);
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<PageResponse<DetalleReservaDto>> getAllDetallesReserva(
            @PathVariable("page") int page,
            @RequestParam(value = "size", defaultValue = "5") int size) {
        logger.info("Listando todos los detalles de reserva - Página: {}, Tamaño: {}", page, size);

        if (size <= 0) {
            size = Settings.PAGE_SIZE;
        }

        Pageable pageable = PageRequest.of(page - 1, size); // La paginación empieza en 0
        PageResponse<DetalleReservaDto> detalles = detalleReservaService.getAllDetallesReserva(pageable);
        return ResponseEntity.ok(detalles);
    }
}
