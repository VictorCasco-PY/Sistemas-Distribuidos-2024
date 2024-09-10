package com.api.apireservas.utils;

import com.api.apireservas.abstracts.AbstractDto;
import com.api.apireservas.abstracts.AbstractEntity;
import com.api.apireservas.interfaces.IMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class Mapper<E extends AbstractEntity, D extends AbstractDto> implements IMapper<E, D> {
    private ModelMapper modelMapper;
    public Mapper(){
        modelMapper=new ModelMapper();
    }

    public E toEntity(D dto, Class<E> entityClass){
        return modelMapper.map(dto, entityClass);
    }

    public D toDto(E entity, Class<D> dtoClass){
        return modelMapper.map(entity, dtoClass);
    }
}
