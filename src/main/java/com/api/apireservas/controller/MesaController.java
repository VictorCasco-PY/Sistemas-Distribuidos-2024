package com.api.apireservas.controller;

import com.api.apireservas.entity.MesaEntity;
import com.api.apireservas.service.MesaServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/mesas")
public class MesaController {
    @Autowired
    private MesaServiceImpl mesaService;

    @Operation(summary = "Crear una nueva mesa")
    @PostMapping
    public MesaEntity createMesa(@RequestBody MesaEntity mesa){
        return mesaService.createMesa(mesa);
    }

    @Operation(summary = "Obtener una mesa por ID")
    @GetMapping("/{id}")
    public ResponseEntity<MesaEntity> getMesaById(@PathVariable Long id){
        Optional<MesaEntity> mesa = mesaService.getById(id);
        return mesa.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Listar todas las mesas")
    @GetMapping
    public List<MesaEntity> getMesas(){
        return mesaService.getAll();
    }
}
