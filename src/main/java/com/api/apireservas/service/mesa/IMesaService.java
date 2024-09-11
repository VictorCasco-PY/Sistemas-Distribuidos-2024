package com.api.apireservas.service.mesa;

import com.api.apireservas.dto.MesaDto;
import com.api.apireservas.dto.PageResponse;
import org.springframework.data.domain.Pageable;

public interface IMesaService {
    MesaDto createMesa(MesaDto mesaDto);
    MesaDto updateMesa(Long id, MesaDto mesaDto);
    void deleteMesa(Long id);
    MesaDto getMesaById(Long id);
    PageResponse<MesaDto> getAllMesas(Pageable pageable);
}
