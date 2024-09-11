package com.api.apireservas.dto;

import com.api.apireservas.abstracts.AbstractDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MesaDto extends AbstractDto {
    private Long id;
    private int numeroMesa;
    private int capacidad;
    private boolean activo;
}