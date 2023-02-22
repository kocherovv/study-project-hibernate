package org.example.repository.impl;

import com.querydsl.jpa.impl.JPAQuery;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.Label;
import org.example.repository.LabelRepository;
import org.example.repository.RepositoryBase;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

@Slf4j
public class LabelRepositoryImpl extends RepositoryBase<Label, Long> implements LabelRepository {

    private final Session session;

    public LabelRepositoryImpl(Session session) {
        super(Label.class, session);
        this.session = session;
    }

    @Override
    public Label findByName(String name) {
        return session.createQuery("select l from Label l where l.name = :name", Label.class)
            .setParameter(name, name)
            .getSingleResult();
    }

    @Override
    public List<Label> findAllByPostId(Long postId) {
        var sql = "SELECT Label.* FROM Label JOIN PostLabel ON PostLabel.label_id = Label.id WHERE PostLabel.Post_id = ?";

        return null;
    }
}
