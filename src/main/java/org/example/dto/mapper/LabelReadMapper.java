package org.example.dto.mapper;

import lombok.AllArgsConstructor;
import org.example.domain.Label;
import org.example.dto.LabelReadDto;

@AllArgsConstructor
public class LabelReadMapper implements Mapper<Label, LabelReadDto> {

    @Override
    public LabelReadDto mapFrom(Label source) {
        return LabelReadDto.builder()
            .id(source.getId())
            .name(source.getName())
            .build();
    }
}
