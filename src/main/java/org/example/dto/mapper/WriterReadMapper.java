package org.example.dto.mapper;

import lombok.AllArgsConstructor;
import org.example.domain.Post;
import org.example.domain.Writer;
import org.example.dto.WriterReadDto;
import org.example.repository.impl.PostRepositoryImpl;

@AllArgsConstructor
public class WriterReadMapper implements Mapper<Writer, WriterReadDto> {

    private PostRepositoryImpl postRepository;

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
