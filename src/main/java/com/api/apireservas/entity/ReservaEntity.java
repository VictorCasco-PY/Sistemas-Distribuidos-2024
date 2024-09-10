package com.api.apireservas.entity;

import com.api.apireservas.entity.base.BaseEntity;
import com.api.apireservas.interfaces.IEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "reservas")
public class ReservaEntity implements IEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long clienteId;  // Relación con cliente podría mejorarse

    @Column(name = "fecha_reserva", nullable = false)
    private LocalDateTime fechaReserva;

    @Column(nullable = false)
    private String estado;
}
