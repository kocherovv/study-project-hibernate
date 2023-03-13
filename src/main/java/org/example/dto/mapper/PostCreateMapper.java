package org.example.dto.mapper;

import lombok.RequiredArgsConstructor;
import org.example.database.repository.impl.LabelRepositoryImpl;
import org.example.database.repository.impl.WriterRepositoryImpl;
import org.example.domain.Post;
import org.example.dto.PostCreateDto;

@RequiredArgsConstructor
public class PostCreateMapper implements Mapper<PostCreateDto, Post> {

    private final LabelRepositoryImpl labelRepository;

    private final WriterRepositoryImpl writerRepository;

    @Override
    public Post mapFrom(PostCreateDto dto) {
        return Post.builder()
            .writer(writerRepository.findById(dto.getWriterId())
                .orElseThrow(IllegalArgumentException::new))
            .content(dto.getContent())
            .labels(dto.getLabels_id().stream()
                .map(id -> labelRepository.findById(id)
                    .orElseThrow(IllegalArgumentException::new))
                .toList())
            .build();
    }
}

