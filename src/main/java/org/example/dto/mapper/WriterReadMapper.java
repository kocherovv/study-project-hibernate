package org.example.dto.mapper;

import org.example.domain.Post;
import org.example.domain.Writer;
import org.example.dto.WriterReadDto;

public class WriterReadMapper implements Mapper<Writer, WriterReadDto> {

    @Override
    public WriterReadDto mapFrom(Writer source) {
        return WriterReadDto.builder()
            .id(source.getId())
            .firstName(source.getFirstName())
            .lastName(source.getLastName())
            .posts_id(source.getPosts().stream()
                .map(Post::getId)
                .toList())
            .build();
    }
}
