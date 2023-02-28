package org.example.utils;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.mapper.*;
import org.example.repository.impl.LabelRepositoryImpl;
import org.example.repository.impl.PostRepositoryImpl;
import org.example.repository.impl.WriterRepositoryImpl;
import org.example.service.LabelService;
import org.example.service.PostService;

import javax.transaction.Transactional;

@Slf4j
public class HibernateRunner {

    @Transactional
    public static void main(String[] args) {
        var session = HibernateUtils.getProxySession();

        session.beginTransaction();

        var postRepository = new PostRepositoryImpl(session);
        var labelRepository = new LabelRepositoryImpl(session);
        var writerRepository = new WriterRepositoryImpl(session);
        var labelCreateMapper = new LabelCreateMapper(postRepository);
        var labelUpdateMapper = new LabelUpdateMapper(postRepository);
        var labelReadMapper = new LabelReadMapper();
        var writerReadMapper = new WriterReadMapper(postRepository);
        var postCreateMapper = new PostCreateMapper(writerReadMapper, labelRepository, writerRepository);
        var postReadMapper = new PostReadMapper(writerReadMapper, labelReadMapper, labelRepository, writerRepository);
        var labelService = new LabelService(labelRepository, labelCreateMapper, labelReadMapper, labelUpdateMapper);
        var postService = new PostService(labelRepository, postRepository, writerRepository, postReadMapper, postCreateMapper);

        postService.deleteById(11L);

        session.getTransaction().commit();
    }
}
