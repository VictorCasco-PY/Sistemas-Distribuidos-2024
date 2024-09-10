package com.api.apireservas.dto;

import com.api.apireservas.interfaces.IDto;
import lombok.Data;

@Data
public class MesaDto implements IDto {
    private Long id;
    private int numeroMesa;
    private int capacidad;
}
