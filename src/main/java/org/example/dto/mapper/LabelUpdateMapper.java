package org.example.dto.mapper;

import lombok.AllArgsConstructor;
import org.example.domain.Label;
import org.example.domain.Post;
import org.example.dto.LabelReadCollectionsDto;
import org.example.repository.impl.PostRepositoryImpl;

@AllArgsConstructor
public class LabelUpdateMapper implements Mapper<Label, LabelReadCollectionsDto> {

    private PostRepositoryImpl postRepository;

    @Override
    public LabelReadCollectionsDto mapFrom(Label entity) {
        return LabelReadCollectionsDto.builder()
            .id(entity.getId())
            .name(entity.getName())
            .posts_id(entity.getPosts().stream()
                .map(Post::getId)
                .toList())
            .build();
    }
}
