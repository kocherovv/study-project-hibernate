package org.example.listeners;

import org.example.domain.Post;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class UpdateDateListener {

    @PrePersist
    public void prePersist(Post entity) {
        entity.setCreated(LocalDateTime.now());
        entity.setUpdated(LocalDateTime.now());
    }

    @PreUpdate
    public void preUpdate(Post entity) {
        entity.setUpdated(LocalDateTime.now());
    }
}
