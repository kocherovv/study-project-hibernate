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
public class PostCreateDto {

    private Long writerId;

    private LocalDateTime created;

    private LocalDateTime updated;

    private String content;

    private PostStatus postStatus;

    @Builder.Default
    private List<Long> labels_id = new ArrayList<>();
}
