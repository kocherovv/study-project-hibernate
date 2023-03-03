package org.example.dto.mapper;

import lombok.AllArgsConstructor;
import org.example.domain.Label;
import org.example.domain.Post;
import org.example.dto.LabelUpdateDto;

@AllArgsConstructor
public class LabelUpdateMapper implements Mapper<Label, LabelUpdateDto> {

    @Override
    public LabelUpdateDto mapFrom(Label entity) {
        return LabelUpdateDto.builder()
            .id(entity.getId())
            .name(entity.getName())
            .posts_id(entity.getPosts().stream()
                .map(Post::getId)
                .toList())
            .build();
    }
}
