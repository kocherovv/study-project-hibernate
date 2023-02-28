package org.example.dto.mapper;

import lombok.AllArgsConstructor;
import org.example.domain.Writer;
import org.example.dto.WriterCreateDto;
import org.example.dto.WriterReadDto;
import org.example.repository.impl.PostRepositoryImpl;

@AllArgsConstructor
public class WriterCreateMapper implements Mapper<WriterCreateDto, Writer> {

    private PostRepositoryImpl postRepository;

    @Override
    public Writer mapFrom(WriterCreateDto entity) {
        return Writer.builder()
            .firstName(entity.getFirstName())
            .lastName(entity.getLastName())
            .build();
    }
}
