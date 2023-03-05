package org.example.repository.impl;

import lombok.extern.log4j.Log4j;
import org.example.domain.Post;
import org.example.repository.PostRepository;
import org.example.repository.RepositoryBase;

import javax.persistence.EntityManager;
import java.util.List;

@Log4j
public class PostRepositoryImpl extends RepositoryBase<Post, Long> implements PostRepository {

    private final EntityManager entityManager;

    public PostRepositoryImpl(EntityManager entityManager) {
        super(Post.class, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public List<Post> findAllByLabelId(Long labelId) {
        var sql =
            "SELECT Post.* FROM Post " +
            "JOIN PostLabel ON PostLabel.post_id = Post.id " +
            "WHERE PostLabel.label_id = :id";

        return entityManager.createNativeQuery(sql, Post.class)
            .setParameter("id", labelId)
            .getResultList();
    }

    @Override
    public List<Post> findAllByWriterId(Long writerId) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteria = criteriaBuilder.createQuery(Post.class);

        var post = criteria.from(Post.class);
        var writer = post.join("writer");

        criteria.select(post).where(
            criteriaBuilder.equal(writer.get("id"), writerId)
        );

        return entityManager.createQuery(criteria)
            .getResultList();
    }
}
