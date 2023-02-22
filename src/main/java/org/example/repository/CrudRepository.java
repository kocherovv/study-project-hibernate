package org.example.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface CrudRepository<E, ID extends Serializable> {

    List<E> findAll();

    Optional<E> findById(ID id);

    E create(E entity);

    E update(E entity);

    void deleteById(ID id);
}
