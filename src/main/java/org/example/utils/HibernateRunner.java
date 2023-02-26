package org.example.utils;

import lombok.extern.slf4j.Slf4j;
import org.example.repository.impl.LabelRepositoryImpl;
import org.example.repository.impl.PostRepositoryImpl;
import org.example.repository.impl.WriterRepositoryImpl;

import javax.transaction.Transactional;

@Slf4j
public class HibernateRunner {

    @Transactional
    public static void main(String[] args) {
        var session = HibernateUtils.getProxySession();

        session.beginTransaction();

        var labelRepository = new LabelRepositoryImpl(session);
        var postRepository = new PostRepositoryImpl(session);
        var writerRepository = new WriterRepositoryImpl(session);

/*            var labelDtoMapper = new LabelDtoMapper();
            var postDtoMapper = new PostDtoMapper();
            var writerDtoMapper = new WriterDtoMapper(postDtoMapper);

            labelDtoMapper.setPostDtoMapper(postDtoMapper);
            postDtoMapper.setLabelDtoMapper(labelDtoMapper);
            postDtoMapper.setWriterDtoMapper(writerDtoMapper);*/

        var post = postRepository.findById(9L).orElse(null);
        postRepository.delete(post);

        session.getTransaction().commit();
    }
}
