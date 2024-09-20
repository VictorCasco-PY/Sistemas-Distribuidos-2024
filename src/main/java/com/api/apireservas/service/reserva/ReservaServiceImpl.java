package com.api.apireservas.service.reserva;

import com.api.apireservas.dto.PageResponse;
import com.api.apireservas.dto.ReservaDto;
import com.api.apireservas.entity.ReservaEntity;
import com.api.apireservas.exceptions.NotFoundException;
import com.api.apireservas.repository.ReservaRepository;
import com.api.apireservas.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaServiceImpl implements IReservaService {


    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private Mapper<ReservaEntity, ReservaDto> mapper;

    @Override
    public ReservaDto createReserva(ReservaDto reservaDto) {
        ReservaEntity reservaEntity = mapper.toEntity(reservaDto, ReservaEntity.class);
        reservaEntity = reservaRepository.save(reservaEntity);
        return mapper.toDto(reservaEntity, ReservaDto.class);
    }

    @Override
    @CachePut(value = "reserva", key = "'api_reserva_' + #id", cacheManager = "cacheManager") // TTL de 30 minutos
    public ReservaDto updateReserva(Long id, ReservaDto reservaDto) {
        ReservaEntity reservaEntity = reservaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Reserva no encontrada"));

        // Verificar si la reserva está inactiva
        if (!reservaEntity.isActivo()) {
            throw new NotFoundException("Reserva no encontrada o inactiva");
        }

        // Actualizar los campos necesarios
        reservaEntity.setClienteId(reservaDto.getClienteId());
        reservaEntity.setFechaReserva(reservaDto.getFechaReserva());
        reservaEntity.setEstado(reservaDto.getEstado());
        reservaEntity = reservaRepository.save(reservaEntity);
        return mapper.toDto(reservaEntity, ReservaDto.class);
    }

    @Override
    @CacheEvict(value = "mesa", key = "'api_reserva_' + #id", cacheManager = "cacheManagerWithSecondsTTL")     // TTL de 60 segundos
    public void deleteReserva(Long id) {
        ReservaEntity reservaEntity = reservaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Reserva no encontrada"));

        reservaEntity.setActivo(false);
        reservaRepository.save(reservaEntity);
    }

    @Override
    @Cacheable(value = "reserva", key = "'api_reserva_' + #id", cacheManager = "cacheManagerNoTTL") // TTL de 2 horas
    public ReservaDto getReservaById(Long id) {
        ReservaEntity reservaEntity = reservaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Reserva no encontrada"));

        if (!reservaEntity.isActivo()) {
            throw new NotFoundException("Reserva no encontrada o inactiva");
        }

        return mapper.toDto(reservaEntity, ReservaDto.class);
    }

    @Override
    public PageResponse<ReservaDto> getAllReservas(Pageable pageable) {
        Page<ReservaEntity> page = reservaRepository.findByActivoTrue(pageable);
        List<ReservaDto> reservas = page
                .map(reserva -> mapper.toDto(reserva, ReservaDto.class))
                .getContent();

        return new PageResponse<>(
                reservas,
                page.getTotalPages(),
                page.getTotalElements(),
                page.getNumber()
        );
    }

    @Override
    public PageResponse<ReservaDto> getReservasByEstado(String estado, Pageable pageable) {
        Page<ReservaEntity> page = reservaRepository.findByEstadoAndActivoTrue(estado, pageable);
        List<ReservaDto> reservas = page
                .map(reserva -> mapper.toDto(reserva, ReservaDto.class))
                .getContent();

        return new PageResponse<>(
                reservas,
                page.getTotalPages(),
                page.getTotalElements(),
                page.getNumber()
        );
    }

}
