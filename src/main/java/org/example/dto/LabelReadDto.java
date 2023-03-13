package org.example.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LabelReadDto {

    private final Long id;

    private final String name;
}