package com.api.apireservas.entity;

import com.api.apireservas.entity.base.BaseEntity;
import com.api.apireservas.interfaces.IEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "mesa")
public class MesaEntity implements IEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int numeroMesa;
    private int capacidad;
}
