package com.api.apireservas.interfaces;

import com.api.apireservas.abstracts.AbstractDto;
import com.api.apireservas.abstracts.AbstractEntity;
import org.springframework.stereotype.Component;

@Component
public interface IMapper<E extends AbstractEntity, D extends AbstractDto> {
    public E toEntity(D dto, Class<E> entityClass);
    public D toDto(E entity, Class<D> dtoClass);
}
