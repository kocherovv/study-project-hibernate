package org.example.util;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.mapper.*;
import org.example.graphs.GraphProperty;
import org.example.graphs.GraphPropertyBuilder;
import org.example.repository.impl.LabelRepositoryImpl;
import org.example.repository.impl.PostRepositoryImpl;
import org.example.repository.impl.WriterRepositoryImpl;
import org.example.service.LabelService;
import org.example.service.PostService;
import org.example.service.WriterService;

import javax.transaction.Transactional;

@Slf4j
public class HibernateRunner {

    @Transactional
    public static void main(String[] args) {
        var session = HibernateUtil.getProxySession();

        session.beginTransaction();

        var postRepository = new PostRepositoryImpl(session);
        var labelRepository = new LabelRepositoryImpl(session);
        var writerRepository = new WriterRepositoryImpl(session);

        var labelCreateMapper = new LabelCreateMapper(postRepository);
        var labelUpdateMapper = new LabelUpdateMapper();
        var labelReadMapper = new LabelReadMapper();
        var writerReadMapper = new WriterReadMapper(postRepository);
        var writerCreateMapper = new WriterCreateMapper(postRepository);
        var postCreateMapper = new PostCreateMapper(writerReadMapper, labelRepository, writerRepository);
        var postReadMapper = new PostReadMapper(writerReadMapper, labelReadMapper, labelRepository, writerRepository);

        var graphPropertyBuilder = new GraphPropertyBuilder(session);

        var labelService = new LabelService(labelRepository, labelCreateMapper, labelReadMapper, labelUpdateMapper, graphPropertyBuilder);
        var postService = new PostService(labelRepository, postRepository, writerRepository, postReadMapper, postCreateMapper, graphPropertyBuilder);
        var writerService = new WriterService(writerRepository, writerReadMapper, writerCreateMapper, graphPropertyBuilder);

        labelService.findById(14L, labelUpdateMapper, GraphProperty.LABEL_WITH_POSTS).ifPresent(System.out::println);

        session.getTransaction().commit();
        System.out.println();
    }
}
