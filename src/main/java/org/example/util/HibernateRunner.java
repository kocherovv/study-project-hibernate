package org.example.util;

import lombok.extern.log4j.Log4j;
import org.example.database.graphs.GraphPropertyBuilder;
import org.example.database.repository.impl.LabelRepositoryImpl;
import org.example.database.repository.impl.PostRepositoryImpl;
import org.example.database.repository.impl.WriterRepositoryImpl;
import org.example.dto.mapper.*;
import org.example.service.LabelService;
import org.example.service.PostService;
import org.example.service.WriterService;

import javax.transaction.Transactional;

@Log4j
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
        var writerReadMapper = new WriterReadMapper();
        var writerCreateMapper = new WriterCreateMapper();
        var postCreateMapper = new PostCreateMapper(labelRepository, writerRepository);
        var postReadMapper = new PostReadMapper(writerReadMapper, labelReadMapper);

        var graphPropertyBuilder = new GraphPropertyBuilder(session);

        var labelService = new LabelService(labelRepository, labelCreateMapper, labelReadMapper, labelUpdateMapper, graphPropertyBuilder);
        var postService = new PostService(labelRepository, postRepository, writerRepository, postReadMapper, postCreateMapper, graphPropertyBuilder);
        var writerService = new WriterService(writerRepository, writerReadMapper, writerCreateMapper, graphPropertyBuilder);

        labelService.deleteById(100L);

        session.getTransaction().commit();
    }
}
