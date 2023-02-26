package org.example.dto.mapper;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.example.domain.Post;
import org.example.dto.PostDto;

@RequiredArgsConstructor
public class PostDtoMapper implements Mapper<Post, PostDto> {

    @Setter
    private LabelDtoMapper labelDtoMapper;

    @Setter
    private WriterDtoMapper writerDtoMapper;

    @Override
    public PostDto map(Post source) {
        return PostDto.builder()
            .id(source.getId())
            .content(source.getContent())
            .postStatus(source.getPostStatus())
            .created(source.getCreated())
            .updated(source.getUpdated())
            .writerDto(writerDtoMapper.map(source.getWriter()))
            .labels(source.getLabels()
                .stream()
                .map(labelDtoMapper::map)
                .toList())
            .build();
    }
}
