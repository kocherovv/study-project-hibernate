package org.example.repository;

import org.example.domain.Post;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long> {

    List<Post> findAllByLabelId(Long labelId);

    List<Post> findAllByWriterId(Long writerId);

}


