package org.example.database.graphs;

import lombok.AllArgsConstructor;
import org.example.domain.Label;
import org.hibernate.Session;
import org.hibernate.graph.RootGraph;

@AllArgsConstructor
public class LabelGraphs {

    private Session session;

    public RootGraph<Label> withPosts() {
        var entityGraphs = session.createEntityGraph(Label.class);
        entityGraphs.addAttributeNode("posts");

        return entityGraphs;
    }
}
