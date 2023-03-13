package org.example.domain;

import lombok.*;
import org.example.domain.enums.PostStatus;
import org.example.listeners.PostListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"labels", "writer"})
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "post")
@EntityListeners(PostListener.class)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private Writer writer;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "updated")
    private LocalDateTime updated;

    @Column(name = "content")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "post_status")
    private PostStatus postStatus;

    @Builder.Default
    @ManyToMany
    @JoinTable(
        name = "post_label",
        joinColumns = @JoinColumn(name = "post_id"),
        inverseJoinColumns = @JoinColumn(name = "label_id")
    )
    private List<Label> labels = new ArrayList<>();

    public void addLabel(Label label) {
        labels.add(label);
        label.getPosts().add(this);
    }
}
