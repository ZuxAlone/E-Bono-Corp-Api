package com.ebono.bonosapi.converter;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractConvecter<E, RQ, RS> {

    public abstract RS fromEntity(E entity);
    public abstract E fromRequest(RQ request);

    public List<RS> fromEntity(List<E> entitys){
        return entitys.stream()
                .map(e -> fromEntity(e))
                .collect(Collectors.toList());
    }

    public List<E> fromRequest(List<RQ> request){
        return request.stream()
                .map(e -> fromRequest(e))
                .collect(Collectors.toList());
    }
}
