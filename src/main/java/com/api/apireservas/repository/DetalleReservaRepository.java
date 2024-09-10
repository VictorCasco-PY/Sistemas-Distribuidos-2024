package com.api.apireservas.repository;

import com.api.apireservas.entity.DetalleReservaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleReservaRepository extends JpaRepository<DetalleReservaEntity, Long> {
}
