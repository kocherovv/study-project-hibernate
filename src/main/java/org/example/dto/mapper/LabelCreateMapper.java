package org.example.dto.mapper;

import lombok.RequiredArgsConstructor;
import org.example.database.repository.impl.PostRepositoryImpl;
import org.example.domain.Label;
import org.example.dto.LabelCreateDto;

@RequiredArgsConstructor
public class LabelCreateMapper implements Mapper<LabelCreateDto, Label> {

    private final PostRepositoryImpl postRepository;

    @Override
    public Label mapFrom(LabelCreateDto dto) {

        return Label.builder()
            .name(dto.getName())
            .posts(dto.getPosts_id().stream()
                .map(id -> postRepository.findById(id).orElseThrow(IllegalArgumentException::new))
                .toList())
            .build();
    }
}
