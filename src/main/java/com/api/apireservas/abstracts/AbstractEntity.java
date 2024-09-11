package com.api.apireservas.abstracts;

import jakarta.persistence.*;
import lombok.Data;


@Data
@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private boolean activo = true;
}
