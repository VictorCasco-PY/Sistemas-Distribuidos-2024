package com.api.apireservas.service.mesa;

import com.api.apireservas.dto.MesaDto;
import com.api.apireservas.dto.PageResponse;
import com.api.apireservas.entity.MesaEntity;
import com.api.apireservas.exceptions.NotFoundException;
import com.api.apireservas.repository.MesaRepository;
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
    @CachePut(value = "mesa", key = "'api_mesa_' + #id", cacheManager = "cacheManager") // TTL de 30 minutos
    public MesaDto updateMesa(Long id, MesaDto mesaDto) {
        MesaEntity mesaEntity = mesaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mesa no encontrada"));
        mesaEntity.setNumeroMesa(mesaDto.getNumeroMesa());
        mesaEntity.setCapacidad(mesaDto.getCapacidad());
        mesaEntity = mesaRepository.save(mesaEntity);
        return mapper.toDto(mesaEntity, MesaDto.class);
    }


    @Override
    @CacheEvict(value = "mesa", key = "'api_mesa_' + #id", cacheManager = "cacheManagerWithSecondsTTL")     // TTL de 60 segundos
    public void deleteMesa(Long id) {
        MesaEntity mesaEntity = mesaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mesa no encontrada"));
        mesaEntity.setActivo(false);
        mesaRepository.save(mesaEntity);
    }


    @Override
    @Cacheable(value = "mesa", key = "'api_mesa_' + #id", cacheManager = "cacheManagerWithHoursTTL") // TTL de 2 horas
    public MesaDto getMesaById(Long id) {
        MesaEntity mesaEntity = mesaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Mesa no encontrada"));

        if (!mesaEntity.isActivo()) {
            throw new NotFoundException("Mesa no encontrada");
        }

        return mapper.toDto(mesaEntity, MesaDto.class);
    }

    @Override
    public PageResponse<MesaDto> getAllMesas(Pageable pageable) {
        Page<MesaEntity> page = mesaRepository.findByActivoTrue(pageable);
        List<MesaDto> mesas = page.map(mesa -> mapper.toDto(mesa, MesaDto.class)).getContent();
        return new PageResponse<>(
                mesas,
                page.getTotalPages(),
                page.getTotalElements(),
                page.getNumber() + 1
        );
    }
}
