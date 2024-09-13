package com.api.apireservas.entity;

import com.api.apireservas.abstracts.AbstractEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "reservas")
public class ReservaEntity extends AbstractEntity {

    private Long clienteId;  // Relación con cliente

    @Column(name = "fecha_reserva", nullable = false)
    private LocalDateTime fechaReserva;

    @Column(nullable = false)
    private String estado;
}
