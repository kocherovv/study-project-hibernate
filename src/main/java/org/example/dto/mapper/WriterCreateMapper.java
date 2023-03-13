package org.example.dto.mapper;

import org.example.domain.Writer;
import org.example.dto.WriterCreateDto;

public class WriterCreateMapper implements Mapper<WriterCreateDto, Writer> {

    @Override
    public Writer mapFrom(WriterCreateDto entity) {
        return Writer.builder()
            .firstName(entity.getFirstName())
            .lastName(entity.getLastName())
            .build();
    }
}
