package org.example.dto;

import lombok.Builder;
import lombok.Data;
import org.example.domain.enums.PostStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class PostReadDto {

    private final Long id;

    private final WriterReadDto writerReadDto;

    private final LocalDateTime created;

    private final LocalDateTime updated;

    private final String content;

    private final PostStatus postStatus;

    @Builder.Default
    private final List<LabelReadDto> labelsDto = new ArrayList<>();
}
