package com.api.apireservas.service.reserva;

import com.api.apireservas.dto.DetalleReservaDto;
import com.api.apireservas.dto.PageResponse;
import com.api.apireservas.dto.ReservaDto;
import com.api.apireservas.entity.DetalleReservaEntity;
import com.api.apireservas.entity.MesaEntity;
import com.api.apireservas.entity.ReservaEntity;
import com.api.apireservas.exceptions.NotFoundException;
import com.api.apireservas.repository.DetalleReservaRepository;
import com.api.apireservas.repository.MesaRepository;
import com.api.apireservas.repository.ReservaRepository;
import com.api.apireservas.utils.Mapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private DetalleReservaRepository detalleReservaRepository;

    @Autowired
    private MesaRepository mesaRepository;

    @Autowired
    private Mapper<ReservaEntity, ReservaDto> reservaMapper;

    @Autowired
    private Mapper<DetalleReservaEntity, DetalleReservaDto> detalleReservaMapper;

    @Value("${page-size}")
    private int pageSize;

    public PageResponse<ReservaDto> getAll(int pageNum) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<ReservaEntity> reservasPage = reservaRepository.findAll(pageable);

        // Verifica si no hay reservas
        if (reservasPage.getTotalElements() == 0) {
            throw new NotFoundException("No hay reservas disponibles."); // Lanza una excepción si no hay elementos
        }

        // Convertir la entidad a DTO utilizando el Mapper y cargar detalles
        List<ReservaDto> reservasDto = reservasPage.getContent().stream()
                .map(reserva -> {
                    ReservaDto dto = reservaMapper.toDto(reserva, ReservaDto.class);
                    // Cargar detalles
                    List<DetalleReservaDto> detallesDto = reserva.getDetalles().stream()
                            .map(detalle -> detalleReservaMapper.toDto(detalle, DetalleReservaDto.class))
                            .collect(Collectors.toList());
                    dto.setDetalles(detallesDto); // Asignar detalles a la reserva
                    return dto;
                })
                .collect(Collectors.toList());

        // Crear la respuesta de página
        return new PageResponse<>(
                reservasDto,
                reservasPage.getTotalPages(),
                reservasPage.getTotalElements(),
                reservasPage.getNumber()
        );
    }

    public ReservaDto getById(Long id) {
        ReservaEntity reservaEntity = reservaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Reserva no encontrada con id " + id));

        // Convertir la reserva a DTO
        ReservaDto reservaDto = reservaMapper.toDto(reservaEntity, ReservaDto.class);

        // Mapear detalles
        List<DetalleReservaDto> detallesDto = reservaEntity.getDetalles().stream()
                .map(detalle -> detalleReservaMapper.toDto(detalle, DetalleReservaDto.class))
                .collect(Collectors.toList());

        reservaDto.setDetalles(detallesDto); // Asegúrate de que se asignen los detalles

        return reservaDto;
    }

    @Transactional
    public ReservaDto create(ReservaDto reservaDto) {
        ReservaEntity reserva = new ReservaEntity();
        reserva.setClienteId(reservaDto.getClienteId());
        reserva.setFechaReserva(reservaDto.getFechaReserva());
        reserva.setEstado(reservaDto.getEstado());

        // Guardar la reserva primero
        ReservaEntity reservaGuardada = reservaRepository.save(reserva);

        // Asignar los detalles a la reserva
        List<DetalleReservaEntity> detalles = new ArrayList<>();
        for (DetalleReservaDto detalleDto : reservaDto.getDetalles()) {
            DetalleReservaEntity detalleReserva = new DetalleReservaEntity();
            MesaEntity mesa = mesaRepository.findById(detalleDto.getMesaId())
                    .orElseThrow(() -> new EntityNotFoundException("Mesa no encontrada"));

            detalleReserva.setMesa(mesa); // Asignar la entidad MesaEntity
            detalleReserva.setNumeroPersonas(detalleDto.getNumeroPersonas());
            detalleReserva.setReserva(reservaGuardada); // Asignar la reserva a cada detalle

            detalles.add(detalleReserva);
        }

        // Asignar la lista de detalles a la reserva y guardarla
        reservaGuardada.setDetalles(detalles); // Asignar la lista de detalles a la reserva
        reservaGuardada = reservaRepository.save(reservaGuardada); // Guardar la reserva con detalles

        // Convertir a DTO y devolver
        return reservaMapper.toDto(reservaGuardada, ReservaDto.class);
    }
    

    public ReservaDto update(Long id, ReservaDto reservaDto) {
        ReservaEntity existingReserva = reservaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Reserva no encontrada con id " + id)); // Cambia la excepción aquí

        existingReserva.setClienteId(reservaDto.getClienteId());
        existingReserva.setFechaReserva(reservaDto.getFechaReserva());
        existingReserva.setEstado(reservaDto.getEstado());

        // Actualizar cabecera de reserva
        reservaRepository.save(existingReserva);

        // Eliminar detalles anteriores
        detalleReservaRepository.deleteByReserva(existingReserva);

        // Guardar los nuevos detalles
        for (DetalleReservaDto detalleDto : reservaDto.getDetalles()) {
            DetalleReservaEntity detalleEntity = detalleReservaMapper.toEntity(detalleDto, DetalleReservaEntity.class);
            detalleEntity.setReserva(existingReserva);

            MesaEntity mesa = mesaRepository.findById(detalleDto.getMesaId())
                    .orElseThrow(() -> new NotFoundException("Mesa no encontrada con id " + detalleDto.getMesaId())); // Cambia la excepción aquí
            detalleEntity.setMesa(mesa);
            detalleReservaRepository.save(detalleEntity);
        }

        return reservaMapper.toDto(existingReserva, ReservaDto.class);
    }

    public void delete(Long id) {
        ReservaEntity reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Reserva no encontrada con id " + id)); // Cambia la excepción aquí
        reserva.setActivo(false); // Marcar como inactiva en lugar de eliminar
        reservaRepository.save(reserva);
    }
}
