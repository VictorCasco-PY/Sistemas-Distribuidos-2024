package com.api.apireservas.entity;

import com.api.apireservas.entity.base.BaseEntity;
import com.api.apireservas.interfaces.IEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "detalle_reserva")
public class DetalleReservaEntity implements IEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reserva_id", nullable = false)
    private ReservaEntity reserva;

    @ManyToOne
    @JoinColumn(name = "mesa_id", nullable = false)
    private MesaEntity mesa;

    private int numeroPersonas;
}
