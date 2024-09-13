package com.api.apireservas.service.mesa;

import com.api.apireservas.dto.MesaDto;
import com.api.apireservas.dto.PageResponse;
import com.api.apireservas.entity.MesaEntity;
import com.api.apireservas.repository.MesaRepository;
import com.api.apireservas.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MesaServiceImpl implements IMesaService {

    @Autowired
    private MesaRepository mesaRepository;

    @Autowired
    private Mapper<MesaEntity, MesaDto> mapper;

    @Override
    public MesaDto createMesa(MesaDto mesaDto) {
        MesaEntity mesaEntity = mapper.toEntity(mesaDto, MesaEntity.class);
        mesaEntity = mesaRepository.save(mesaEntity);
        return mapper.toDto(mesaEntity, MesaDto.class);
    }

    @Override
    public MesaDto updateMesa(Long id, MesaDto mesaDto) {
        MesaEntity mesaEntity = mesaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mesa no encontrada"));
        mesaEntity.setNumeroMesa(mesaDto.getNumeroMesa());
        mesaEntity.setCapacidad(mesaDto.getCapacidad());
        mesaEntity = mesaRepository.save(mesaEntity);
        return mapper.toDto(mesaEntity, MesaDto.class);
    }

    @Override
    public void deleteMesa(Long id) {
        MesaEntity mesaEntity = mesaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mesa no encontrada"));
        mesaEntity.setActivo(false);  // Borrado lógico usando 'activo'
        mesaRepository.save(mesaEntity);
    }


    @Override
    //@Cacheable(value = "mesa", key = "'api_mesa ' + #id")
    public MesaDto getMesaById(Long id) {
        return mesaRepository.findById(id)
                .map(mesa -> mapper.toDto(mesa, MesaDto.class))
                .orElseThrow(() -> new RuntimeException("Mesa no encontrada"));
    }

    @Override
    public PageResponse<MesaDto> getAllMesas(Pageable pageable) {
        Page<MesaEntity> page = mesaRepository.findAll(pageable);
        List<MesaDto> mesas = page.map(mesa -> mapper.toDto(mesa, MesaDto.class)).getContent();
        return new PageResponse<>(
                mesas,
                page.getTotalPages(),
                page.getTotalElements(),
                page.getNumber()
        );
    }
}
