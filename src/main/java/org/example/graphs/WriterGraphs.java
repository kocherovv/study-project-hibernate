package org.example.graphs;

import lombok.AllArgsConstructor;
import org.example.domain.Writer;
import org.hibernate.Session;
import org.hibernate.graph.RootGraph;

@AllArgsConstructor
public class WriterGraphs {

    private Session session;

    public RootGraph<Writer> withPosts() {
        var entityGraphs = session.createEntityGraph(Writer.class);
        entityGraphs.addAttributeNode("posts");

        return entityGraphs;
    }
}
