package org.example.repository;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class RepositoryBase<E, ID extends Serializable> implements CrudRepository<E, ID> {

    private final Class<E> clazz;
    private final Session session;

    @Override
    public List<E> findAll() {
        var criteriaQuery = session.getCriteriaBuilder().createQuery(clazz);
        criteriaQuery.from(clazz);

        return session.createQuery(criteriaQuery)
            .getResultList();
    }

    @Override
    public Optional<E> findById(ID id) {
        return Optional.ofNullable(session.find(clazz, id));
    }

    @Override
    public E create(E entity) {
        session.persist(entity);

        return entity;
    }

    @Override
    public E update(E entity) {
        session.update(entity);

        return entity;
    }

    @Override
    public void deleteById(ID id) {
        session.delete(id);
        session.flush();
    }
}
