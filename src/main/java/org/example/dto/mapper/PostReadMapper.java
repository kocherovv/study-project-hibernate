package org.example.dto.mapper;

import lombok.RequiredArgsConstructor;
import org.example.domain.Post;
import org.example.dto.PostReadDto;

@RequiredArgsConstructor
public class PostReadMapper implements Mapper<Post, PostReadDto> {

    private final WriterReadMapper writerReadMapper;

    private final LabelReadMapper labelReadMapper;

    @Override
    public PostReadDto mapFrom(Post source) {
        return PostReadDto.builder()
            .id(source.getId())
            .content(source.getContent())
            .postStatus(source.getPostStatus())
            .created(source.getCreated())
            .updated(source.getUpdated())
            .writerReadDto(writerReadMapper.mapFrom(source.getWriter()))
            .labelsDto(source.getLabels().stream()
                .map(labelReadMapper::mapFrom)
                .toList())
            .build();
    }
}
