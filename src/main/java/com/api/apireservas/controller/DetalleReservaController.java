package com.api.apireservas.controller;

import com.api.apireservas.dto.DetalleReservaDto;
import com.api.apireservas.dto.PageResponse;
import com.api.apireservas.service.reserva.IDetalleReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/detalles-reserva")
public class DetalleReservaController {
    @Autowired
    private IDetalleReservaService detalleReservaService;

    @PostMapping
    public ResponseEntity<DetalleReservaDto> createDetalleReserva(@RequestBody DetalleReservaDto detalleReservaDto) {
        DetalleReservaDto createdDetalle = detalleReservaService.createDetalleReserva(detalleReservaDto);
        return ResponseEntity.ok(createdDetalle);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalleReservaDto> updateDetalleReserva(@PathVariable Long id, @RequestBody DetalleReservaDto detalleReservaDto) {
        DetalleReservaDto updatedDetalle = detalleReservaService.updateDetalleReserva(id, detalleReservaDto);
        return ResponseEntity.ok(updatedDetalle);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDetalleReserva(@PathVariable Long id) {
        detalleReservaService.deleteDetalleReserva(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleReservaDto> getDetalleReservaById(@PathVariable Long id) {
        DetalleReservaDto detalleReserva = detalleReservaService.getDetalleReservaById(id);
        return ResponseEntity.ok(detalleReserva);
    }

    @GetMapping
    public ResponseEntity<PageResponse<DetalleReservaDto>> getAllDetallesReserva(Pageable pageable) {
        PageResponse<DetalleReservaDto> detalles = detalleReservaService.getAllDetallesReserva(pageable);
        return ResponseEntity.ok(detalles);
    }
}
