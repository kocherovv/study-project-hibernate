package org.example.service;

import lombok.Getter;
import org.example.database.graphs.GraphPropertyBuilder;
import org.example.database.repository.impl.LabelRepositoryImpl;
import org.example.database.repository.impl.PostRepositoryImpl;
import org.example.database.repository.impl.WriterRepositoryImpl;
import org.example.dto.mapper.*;
import org.hibernate.Session;

import static org.mockito.Mockito.mock;

@Getter
public abstract class AbstractTestBase {

    private PostRepositoryImpl postRepository;
    private LabelRepositoryImpl labelRepository;
    private WriterRepositoryImpl writerRepository;

    private LabelCreateMapper labelCreateMapper;
    private LabelUpdateMapper labelUpdateMapper;
    private LabelReadMapper labelReadMapper;
    private WriterReadMapper writerReadMapper;
    private WriterCreateMapper writerCreateMapper;
    private PostCreateMapper postCreateMapper;
    private PostReadMapper postReadMapper;

    private LabelService labelService;
    private PostService postService;
    private WriterService writerService;

    private GraphPropertyBuilder graphPropertyBuilder;

    public final void buildTestContainer(Session session) {
        postRepository = mock(PostRepositoryImpl.class);
        labelRepository = mock(LabelRepositoryImpl.class);
        writerRepository = mock(WriterRepositoryImpl.class);

        labelCreateMapper = new LabelCreateMapper(postRepository);
        labelUpdateMapper = new LabelUpdateMapper();
        labelReadMapper = new LabelReadMapper();
        writerReadMapper = new WriterReadMapper();
        writerCreateMapper = new WriterCreateMapper();
        postCreateMapper = new PostCreateMapper(labelRepository, writerRepository);
        postReadMapper = new PostReadMapper(writerReadMapper, labelReadMapper);
        graphPropertyBuilder = new GraphPropertyBuilder(session);
        labelService = new LabelService(labelRepository, labelCreateMapper, labelReadMapper, labelUpdateMapper, graphPropertyBuilder);
        postService = new PostService(labelRepository, postRepository, writerRepository, postReadMapper, postCreateMapper, graphPropertyBuilder);
        writerService = new WriterService(writerRepository, writerReadMapper, writerCreateMapper, graphPropertyBuilder);
    }
}
