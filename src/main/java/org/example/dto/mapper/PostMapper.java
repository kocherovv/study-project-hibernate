package org.example.dto.mapper;

import lombok.RequiredArgsConstructor;
import org.example.domain.Post;
import org.example.dto.PostDto;

@RequiredArgsConstructor
public class PostMapper implements Mapper<PostDto, Post> {

    private final LabelMapper labelMapper;
    private final WriterMapper writerMapper;

    @Override
    public Post map(PostDto source) {
        return Post.builder()
            .id(source.getId())
            .content(source.getContent())
            .postStatus(source.getPostStatus())
            .created(source.getCreated())
            .updated(source.getUpdated())
            .writer(writerMapper.map(source.getWriterDto()))
            .labels(source.getLabels().stream()
                .map(labelMapper::map)
                .toList())
            .build();
    }
}

