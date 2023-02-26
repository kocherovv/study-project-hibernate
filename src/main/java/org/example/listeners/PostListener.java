package org.example.listeners;

import org.example.domain.Post;
import org.example.domain.enums.PostStatus;
import org.example.repository.impl.LabelRepositoryImpl;
import org.example.utils.HibernateUtils;
import org.hibernate.Session;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class PostListener {

    private final Session session = HibernateUtils.getProxySession();
    private final LabelRepositoryImpl labelRepository = new LabelRepositoryImpl(session);

    @PrePersist
    public void prePersist(Post entity) {
        entity.setCreated(LocalDateTime.now());
        entity.setUpdated(LocalDateTime.now());
        entity.setPostStatus(PostStatus.ACTIVE);
    }

    @PreUpdate
    public void preUpdate(Post entity) {
        entity.setUpdated(LocalDateTime.now());
    }
}
