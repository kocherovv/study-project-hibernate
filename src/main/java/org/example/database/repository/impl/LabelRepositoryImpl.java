package org.example.database.repository.impl;

import lombok.extern.log4j.Log4j;
import org.example.database.repository.LabelRepository;
import org.example.database.repository.RepositoryBase;
import org.example.domain.Label;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Log4j
public class LabelRepositoryImpl extends RepositoryBase<Label, Long> implements LabelRepository {

    private final EntityManager entityManager;

    public LabelRepositoryImpl(EntityManager entityManager) {
        super(Label.class, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Label> findByName(String name) {
        return Optional.ofNullable(
            entityManager.createQuery("select l from Label l where l.name = :name", Label.class)
                .setParameter("name", name)
                .getSingleResult());
    }

    @Override
    public List<Label> findAllByPostId(Long postId) {
        var sql = "SELECT label.* " +
            "FROM label RIGHT JOIN post_label " +
            "ON post_label.label_id = label.id " +
            "WHERE post_label.post_id = :id";

        return entityManager.createNativeQuery(sql, Label.class)
            .setParameter("id", postId)
            .getResultList();
    }
}
