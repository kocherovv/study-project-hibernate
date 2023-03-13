package org.example.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class WriterCreateDto {

    private final String firstName;

    private final String lastName;
}
