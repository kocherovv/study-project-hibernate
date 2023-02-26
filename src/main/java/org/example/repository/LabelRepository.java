package org.example.repository;

import org.example.domain.Label;

import java.util.List;
import java.util.Optional;

public interface LabelRepository extends CrudRepository<Label, Long> {

    Optional<Label> findByName(String name);

    List<Label> findAllByPostId(Long postId);
}
