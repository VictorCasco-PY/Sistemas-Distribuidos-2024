package com.api.apireservas.abstracts;

import com.api.apireservas.interfaces.IEntity;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@MappedSuperclass
@Data
public abstract class AbstractEntity implements IEntity {
}
