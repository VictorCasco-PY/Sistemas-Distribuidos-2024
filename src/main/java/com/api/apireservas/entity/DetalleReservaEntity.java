package com.api.apireservas.entity;

import com.api.apireservas.abstracts.AbstractEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "detalle_reserva")
public class DetalleReservaEntity extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reserva_id")
    private ReservaEntity reserva;


    @ManyToOne
    @JoinColumn(name = "mesa_id", nullable = false)
    private MesaEntity mesa;

    private int numeroPersonas;

    public MesaEntity getMesa() {
        return mesa;
    }

    public void setMesa(MesaEntity mesa) {
        this.mesa = mesa;
    }
}
