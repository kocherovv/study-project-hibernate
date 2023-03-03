package org.example.dto.mapper;

import lombok.AllArgsConstructor;
import org.example.domain.Label;
import org.example.dto.LabelCreateDto;
import org.example.repository.impl.PostRepositoryImpl;

@AllArgsConstructor
public class LabelCreateMapper implements Mapper<LabelCreateDto, Label> {

    private PostRepositoryImpl postRepository;

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
