package org.example.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = "posts")
@EqualsAndHashCode(of = "name")
@Entity
@Table(name = "label")
public class Label {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Builder.Default
    @ManyToMany(mappedBy = "labels")
    private List<Post> posts = new ArrayList<>();

    public void addPost(Post post) {
        posts.add(post);
        post.getLabels().add(this);
    }
}
