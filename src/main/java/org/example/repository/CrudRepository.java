package org.example.repository;

import org.example.dto.mapper.Mapper;

import java.io.Serializable;
import java.util.*;

public interface CrudRepository<E, ID extends Serializable> {

    List<E> findAll();

    default Optional<E> findById(ID id) {
        return findById(id, Collections.emptyMap());
    }

    Optional<E> findById(ID id, Map<String, Object> properties);

    E create(E entity);

    E update(E entity);

    void delete(E entity);

    void merge(E entity);
}
