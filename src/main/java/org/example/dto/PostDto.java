package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.example.domain.enums.PostStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
@Builder
public class PostDto {

    private Long id;

    private WriterDto writerDto;

    private LocalDateTime created;

    private LocalDateTime updated;

    private String content;

    private PostStatus postStatus;

    @Builder.Default
    private List<LabelDto> labels = new ArrayList<>();
}
