package org.example.graphs;

import org.hibernate.Session;
import org.hibernate.graph.GraphSemantic;

import java.util.Map;

public class GraphPropertyBuilder {

    private final LabelGraphs labelGraphs;
    private final PostGraphs postGraphs;
    private final WriterGraphs writerGraphs;

    public GraphPropertyBuilder(Session session) {
        labelGraphs = new LabelGraphs(session);
        postGraphs = new PostGraphs(session);
        writerGraphs = new WriterGraphs(session);
    }

    public Map<String, Object> getProperty(GraphProperty graphProperty) {
        Object rootGraph = null;

        switch (graphProperty) {
            case LABEL_WITH_POSTS -> rootGraph = labelGraphs.withPosts();
            case POST_WITH_LABELS -> rootGraph = postGraphs.withLabels();
            case POST_WITH_WRITER -> rootGraph = postGraphs.withWriter();
            case POST_WITH_LABELS_WRITERS -> rootGraph = postGraphs.withLabelsAndWriter();
            case WRITER_WITH_POST -> rootGraph = writerGraphs.withPosts();
        }

        return Map.of(GraphSemantic.LOAD.getJpaHintName(), rootGraph);
    }
}
