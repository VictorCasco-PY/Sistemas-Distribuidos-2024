package com.api.apireservas.dto;

import com.api.apireservas.abstracts.AbstractDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaDto extends AbstractDto {
    private Long id;
    private Long clienteId;
    private LocalDateTime fechaReserva;
    private String estado;
    private List<DetalleReservaDto> detalles;
}