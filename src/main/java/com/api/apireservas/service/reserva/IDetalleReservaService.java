package com.api.apireservas.service.reserva;

import com.api.apireservas.dto.DetalleReservaDto;
import com.api.apireservas.dto.PageResponse;
import org.springframework.data.domain.Pageable;


public interface IDetalleReservaService {
    DetalleReservaDto createDetalleReserva(DetalleReservaDto detalleReservaDto);
    DetalleReservaDto updateDetalleReserva(Long id, DetalleReservaDto detalleReservaDto);
    void deleteDetalleReserva(Long id);
    DetalleReservaDto getDetalleReservaById(Long id);
    PageResponse<DetalleReservaDto> getAllDetallesReserva(Pageable pageable);
}