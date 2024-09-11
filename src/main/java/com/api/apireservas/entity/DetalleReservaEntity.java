package com.api.apireservas.entity;

import com.api.apireservas.abstracts.AbstractEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "detalle_reserva")
public class DetalleReservaEntity extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "reserva_id", nullable = false)
    private ReservaEntity reserva;

    @ManyToOne
    @JoinColumn(name = "mesa_id", nullable = false)
    private MesaEntity mesa;

    private int numeroPersonas;
}
