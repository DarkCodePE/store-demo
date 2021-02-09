package com.codmind.api_order.converters;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractConverter<E,D>{
    public abstract D frontEntity(E entity);
    public abstract E frontDTO(D dto);
    public List<D> frontEntity(List<E> entities){
        return entities.stream()
                .map(e -> frontEntity(e))
                .collect(Collectors.toList());
    }
    public List<E> frontDTO(List<D> dtos){
        return dtos.stream()
                .map(d -> frontDTO(d))
                .collect(Collectors.toList());
    }
}
