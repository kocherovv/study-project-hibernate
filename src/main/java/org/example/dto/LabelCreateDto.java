package org.example.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class LabelCreateDto {

    private final String name;

    @Builder.Default
    private final List<Long> posts_id = new ArrayList<>();
}