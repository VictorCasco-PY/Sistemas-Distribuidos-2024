package com.api.apireservas.service.reserva;

import com.api.apireservas.dto.PageResponse;
import com.api.apireservas.dto.ReservaDto;
import com.api.apireservas.entity.ReservaEntity;
import com.api.apireservas.repository.ReservaRepository;
import com.api.apireservas.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public ReservaDto updateReserva(Long id, ReservaDto reservaDto) {
        Optional<ReservaEntity> reservaOpt = reservaRepository.findById(id);
        if (reservaOpt.isPresent()) {
            ReservaEntity reservaEntity = reservaOpt.get();
            // Actualizar los campos necesarios
            reservaEntity.setClienteId(reservaDto.getClienteId());
            reservaEntity.setFechaReserva(reservaDto.getFechaReserva());
            reservaEntity.setEstado(reservaDto.getEstado());
            reservaEntity = reservaRepository.save(reservaEntity);
            return mapper.toDto(reservaEntity, ReservaDto.class);
        }
        throw new RuntimeException("Reserva no encontrada");
    }

    @Override
    public void deleteReserva(Long id) {
        Optional<ReservaEntity> reservaOpt = reservaRepository.findById(id);
        if (reservaOpt.isPresent()) {
            ReservaEntity reservaEntity = reservaOpt.get();
            reservaEntity.setActivo(true);  // Borrado lógico
            reservaRepository.save(reservaEntity);
        } else {
            throw new RuntimeException("Reserva no encontrada");
        }
    }

    @Override
    public ReservaDto getReservaById(Long id) {
        return reservaRepository.findById(id)
                .map(reserva -> mapper.toDto(reserva, ReservaDto.class))
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
    }

    @Override
    public PageResponse<ReservaDto> getAllReservas(Pageable pageable) {
        Page<ReservaEntity> page = reservaRepository.findAll(pageable);
        List<ReservaDto> reservas = page.map(reserva -> mapper.toDto(reserva, ReservaDto.class)).getContent();
        return new PageResponse<>(
                reservas,
                page.getTotalPages(),
                page.getTotalElements(),
                page.getNumber()
        );
    }
}
