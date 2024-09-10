package com.api.apireservas.interfaces;

import com.api.apireservas.abstracts.AbstractDto;
import com.api.apireservas.dto.PageResponse;
import org.springframework.data.domain.PageRequest;

public interface IService<T extends AbstractDto> {
    T create(T dto);
    T getById(Long id);

    PageResponse<T> getAll(int page);

    T update(Long id, T dto);

    boolean delete(Long id);


}
