package org.example.dto;

import lombok.Builder;
import lombok.Data;
import org.example.domain.enums.PostStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class PostCreateDto {

    private final Long writerId;

    private final LocalDateTime created;

    private final LocalDateTime updated;

    private final String content;

    private final PostStatus postStatus;

    @Builder.Default
    private final List<Long> labels_id = new ArrayList<>();
}
