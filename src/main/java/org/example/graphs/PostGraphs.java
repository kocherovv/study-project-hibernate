package org.example.graphs;

import lombok.AllArgsConstructor;
import org.example.domain.Post;
import org.hibernate.Session;
import org.hibernate.graph.RootGraph;

@AllArgsConstructor
public class PostGraphs {

    private Session session;

    public RootGraph<Post> withLabels() {
        var entityGraphs = session.createEntityGraph(Post.class);
        entityGraphs.addAttributeNode("labels");

        return entityGraphs;
    }

    public RootGraph<Post> withWriter() {
        var entityGraphs = session.createEntityGraph(Post.class);
        entityGraphs.addAttributeNode("writer");

        return entityGraphs;
    }

    public RootGraph<Post> withLabelsAndWriter() {
        var entityGraphs = session.createEntityGraph(Post.class);
        entityGraphs.addAttributeNode("labels");
        entityGraphs.addAttributeNode("writer");

        return entityGraphs;
    }
}
