package org.example.dto.mapper;

import lombok.AllArgsConstructor;
import org.example.domain.Post;
import org.example.dto.PostReadDto;

@AllArgsConstructor
public class PostReadMapper implements Mapper<Post, PostReadDto> {

    private WriterReadMapper writerReadMapper;

    private LabelReadMapper labelReadMapper;

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
