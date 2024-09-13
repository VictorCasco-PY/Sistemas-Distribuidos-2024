package com.api.apireservas.controller;

import com.api.apireservas.dto.MesaDto;
import com.api.apireservas.dto.PageResponse;
import com.api.apireservas.entity.MesaEntity;
import com.api.apireservas.service.mesa.IMesaService;
import com.api.apireservas.service.mesa.MesaServiceImpl;
import com.api.apireservas.utils.Settings;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/mesas")
public class MesaController {

    private static final Logger logger = LoggerFactory.getLogger(MesaController.class);

    @Autowired
    private IMesaService mesaService;

    @PostMapping
    public ResponseEntity<MesaDto> createMesa(@RequestBody MesaDto mesaDto) {
        logger.info("Creando una nueva mesa: {}", mesaDto);
        MesaDto createdMesa = mesaService.createMesa(mesaDto);
        return ResponseEntity.ok(createdMesa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MesaDto> updateMesa(@PathVariable Long id, @RequestBody MesaDto mesaDto) {
        logger.info("Actualizando mesa con id: {}", id);
        MesaDto updatedMesa = mesaService.updateMesa(id, mesaDto);
        return ResponseEntity.ok(updatedMesa);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMesa(@PathVariable Long id) {
        logger.warn("Borrando mesa con id: {}", id);
        mesaService.deleteMesa(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MesaDto> getMesaById(@PathVariable Long id) {
        logger.info("Obteniendo mesa con id: {}", id);
        MesaDto mesa = mesaService.getMesaById(id);
        return ResponseEntity.ok(mesa);
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<PageResponse<MesaDto>> getAllMesas(
            @PathVariable("page") int page,
            @RequestParam(value = "size", defaultValue = "5") int size) {
        logger.info("Listando todas las mesas - Página: {}, Tamaño: {}", page, size);

        if (size <= 0) {
            size = Settings.PAGE_SIZE;
        }

        Pageable pageable = PageRequest.of(page - 1, size); // La paginación empieza en 0
        PageResponse<MesaDto> mesas = mesaService.getAllMesas(pageable);
        return ResponseEntity.ok(mesas);
    }

}
