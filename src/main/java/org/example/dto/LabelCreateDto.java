package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Builder
@Data
public class LabelCreateDto {

    private String name;

    @Builder.Default
    private List<Long> posts_id = new ArrayList<>();
}