package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class LabelReadDto {

    private Long id;

    private String name;
}