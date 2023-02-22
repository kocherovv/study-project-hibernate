package org.example.repository;

import org.example.domain.Label;

import java.util.List;

public interface LabelRepository {

    Label findByName(String name);

    List<Label> findAllByPostId(Long postId);
}
