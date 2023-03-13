package org.example.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
public class WriterReadDto {

    private final Long id;

    private final String firstName;

    private final String lastName;

    @Builder.Default
    private final List<Long> posts_id = new ArrayList<>();
}
