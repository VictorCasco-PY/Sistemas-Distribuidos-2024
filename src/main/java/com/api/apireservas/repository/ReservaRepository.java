package com.api.apireservas.repository;

import com.api.apireservas.entity.ReservaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaRepository extends JpaRepository<ReservaEntity, Long> {
    Page<ReservaEntity> findByActivoTrue(Pageable pageable);

    Page<ReservaEntity> findByEstadoAndActivoTrue(String estado, Pageable pageable);
}
