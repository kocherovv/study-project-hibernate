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
@EqualsAndHashCode(of = {"firstName", "lastName"})
@Entity
@Table(name = "writer")
public class Writer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Builder.Default
    @OneToMany(
        mappedBy = "writer",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<Post> posts = new ArrayList<>();

    public void addPost(Post post) {
        this.posts.add(post);
        post.setWriter(this);
    }
}
