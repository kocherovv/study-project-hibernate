package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Builder
@Data
public class WriterDto {

    private Long id;

    private String firstName;

    private String lastName;

    @Builder.Default
    private List<PostDto> posts = new ArrayList<>();
}
