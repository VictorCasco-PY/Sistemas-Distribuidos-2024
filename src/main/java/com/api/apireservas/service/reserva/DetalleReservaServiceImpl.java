package com.api.apireservas.service.reserva;

import com.api.apireservas.dto.DetalleReservaDto;
import com.api.apireservas.dto.PageResponse;
import com.api.apireservas.entity.DetalleReservaEntity;
import com.api.apireservas.exceptions.NotFoundException;
import com.api.apireservas.repository.DetalleReservaRepository;
import com.api.apireservas.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleReservaServiceImpl implements IDetalleReservaService {

    @Autowired
    private DetalleReservaRepository detalleReservaRepository;

    @Autowired
    private Mapper<DetalleReservaEntity, DetalleReservaDto> mapper;

    @Override
    public DetalleReservaDto createDetalleReserva(DetalleReservaDto detalleReservaDto) {
        DetalleReservaEntity detalleEntity = mapper.toEntity(detalleReservaDto, DetalleReservaEntity.class);
        detalleEntity = detalleReservaRepository.save(detalleEntity);
        return mapper.toDto(detalleEntity, DetalleReservaDto.class);
    }

    @Override
    public DetalleReservaDto updateDetalleReserva(Long id, DetalleReservaDto detalleReservaDto) {
        DetalleReservaEntity detalleEntity = detalleReservaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Detalle de reserva no encontrado"));

        if (!detalleEntity.isActivo()) {
            throw new NotFoundException("Detalle de reserva no encontrado o inactivo");
        }

        detalleEntity.setNumeroPersonas(detalleReservaDto.getNumeroPersonas());
        detalleEntity = detalleReservaRepository.save(detalleEntity);
        return mapper.toDto(detalleEntity, DetalleReservaDto.class);
    }

    @Override
    public void deleteDetalleReserva(Long id) {
        DetalleReservaEntity detalleEntity = detalleReservaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Detalle de reserva no encontrado"));

        detalleEntity.setActivo(false);
        detalleReservaRepository.save(detalleEntity);
    }

    @Override
    public DetalleReservaDto getDetalleReservaById(Long id) {
        DetalleReservaEntity detalleEntity = detalleReservaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Detalle de reserva no encontrado"));

        if (!detalleEntity.isActivo()) {
            throw new NotFoundException("Detalle de reserva no encontrado o inactivo");
        }

        return mapper.toDto(detalleEntity, DetalleReservaDto.class);
    }

    @Override
    public PageResponse<DetalleReservaDto> getAllDetallesReserva(Pageable pageable) {
        Page<DetalleReservaEntity> page = detalleReservaRepository.findByActivoTrue(pageable);
        List<DetalleReservaDto> detalles = page
                .map(detalle -> mapper.toDto(detalle, DetalleReservaDto.class))
                .getContent();

        return new PageResponse<>(
                detalles,
                page.getTotalPages(),
                page.getTotalElements(),
                page.getNumber()
        );
    }
}