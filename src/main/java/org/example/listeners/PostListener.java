package org.example.listeners;

import org.example.domain.Post;
import org.example.domain.enums.PostStatus;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class PostListener {

    @PrePersist
    public void fillPrePersistParameters(Post entity) {
        entity.setCreated(LocalDateTime.now());
        entity.setUpdated(LocalDateTime.now());
        entity.setPostStatus(PostStatus.ACTIVE);
    }

    @PreUpdate
    public void updateLastChangeDate(Post entity) {
        entity.setUpdated(LocalDateTime.now());
    }
}
