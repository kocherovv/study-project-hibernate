package org.example.dto.mapper;

import lombok.AllArgsConstructor;
import org.example.domain.Label;
import org.example.domain.Post;
import org.example.dto.PostCreateDto;
import org.example.dto.PostReadDto;
import org.example.repository.impl.LabelRepositoryImpl;
import org.example.repository.impl.WriterRepositoryImpl;

@AllArgsConstructor
public class PostCreateMapper implements Mapper<PostCreateDto, Post> {

    private WriterReadMapper writerReadMapper;

    private LabelRepositoryImpl labelRepository;

    private WriterRepositoryImpl writerRepository;

    @Override
    public Post mapFrom(PostCreateDto dto) {
        return Post.builder()
            .writer(writerRepository.findById(dto.getWriterReadDto().getId())
                .orElseThrow(IllegalArgumentException::new))
            .content(dto.getContent())
            .labels(dto.getLabels_id().stream()
                .map(id ->labelRepository.findById(id)
                    .orElseThrow(IllegalArgumentException::new))
                .toList())
            .build();
    }
}

