package com.api.apireservas.controller;

import com.api.apireservas.dto.MesaDto;
import com.api.apireservas.dto.PageResponse;
import com.api.apireservas.entity.MesaEntity;
import com.api.apireservas.service.mesa.IMesaService;
import com.api.apireservas.service.mesa.MesaServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/mesas")
public class MesaController {
    @Autowired
    private IMesaService mesaService;

    @PostMapping
    public ResponseEntity<MesaDto> createMesa(@RequestBody MesaDto mesaDto) {
        MesaDto createdMesa = mesaService.createMesa(mesaDto);
        return ResponseEntity.ok(createdMesa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MesaDto> updateMesa(@PathVariable Long id, @RequestBody MesaDto mesaDto) {
        MesaDto updatedMesa = mesaService.updateMesa(id, mesaDto);
        return ResponseEntity.ok(updatedMesa);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMesa(@PathVariable Long id) {
        mesaService.deleteMesa(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MesaDto> getMesaById(@PathVariable Long id) {
        MesaDto mesa = mesaService.getMesaById(id);
        return ResponseEntity.ok(mesa);
    }

    @GetMapping
    public ResponseEntity<PageResponse<MesaDto>> getAllMesas(Pageable pageable) {
        PageResponse<MesaDto> mesas = mesaService.getAllMesas(pageable);
        return ResponseEntity.ok(mesas);
    }
}
