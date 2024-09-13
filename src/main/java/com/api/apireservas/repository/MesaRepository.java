package com.api.apireservas.repository;

import com.api.apireservas.entity.MesaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MesaRepository extends JpaRepository<MesaEntity, Long> {
    Page<MesaEntity> findByActivoTrue(Pageable pageable);

    Optional<MesaEntity> findByIdAndActivoTrue(Long id);
}
