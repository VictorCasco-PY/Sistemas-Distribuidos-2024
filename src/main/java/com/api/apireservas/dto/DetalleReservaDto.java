package com.api.apireservas.dto;

import com.api.apireservas.abstracts.AbstractDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleReservaDto extends AbstractDto {
    private Long id;
    private Long mesaId;
    private int numeroPersonas;
    private boolean activo;
}