package com.api.apireservas.service.reserva;

import com.api.apireservas.dto.PageResponse;
import com.api.apireservas.dto.ReservaDto;
import org.springframework.data.domain.Pageable;

public interface IReservaService {
    ReservaDto createReserva(ReservaDto reservaDto);
    ReservaDto updateReserva(Long id, ReservaDto reservaDto);
    void deleteReserva(Long id);
    ReservaDto getReservaById(Long id);
    PageResponse<ReservaDto> getAllReservas(Pageable pageable);
}
