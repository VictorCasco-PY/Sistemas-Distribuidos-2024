package com.api.apireservas.entity;

import com.api.apireservas.abstracts.AbstractEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "mesa")
public class MesaEntity extends AbstractEntity {

    private int numeroMesa;
    private int capacidad;
}
