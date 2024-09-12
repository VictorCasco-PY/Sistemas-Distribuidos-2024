package com.api.apireservas.controller;

import com.api.apireservas.dto.PageResponse;
import com.api.apireservas.dto.ReservaDto;
import com.api.apireservas.service.reserva.IReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {
    @Autowired
    private IReservaService reservaService;

    @PostMapping
    public ResponseEntity<ReservaDto> createReserva(@RequestBody ReservaDto reservaDto) {
        ReservaDto createdReserva = reservaService.createReserva(reservaDto);
        return ResponseEntity.ok(createdReserva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservaDto> updateReserva(@PathVariable Long id, @RequestBody ReservaDto reservaDto) {
        ReservaDto updatedReserva = reservaService.updateReserva(id, reservaDto);
        return ResponseEntity.ok(updatedReserva);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReserva(@PathVariable Long id) {
        reservaService.deleteReserva(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaDto> getReservaById(@PathVariable Long id) {
        ReservaDto reserva = reservaService.getReservaById(id);
        return ResponseEntity.ok(reserva);
    }

    @GetMapping
    public ResponseEntity<PageResponse<ReservaDto>> getAllReservas(Pageable pageable) {
        PageResponse<ReservaDto> reservas = reservaService.getAllReservas(pageable);
        return ResponseEntity.ok(reservas);
    }
}
