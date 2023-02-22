package org.example.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.domain.Writer;
import org.example.repository.RepositoryBase;
import org.example.repository.WriterRepository;
import org.hibernate.Session;

import java.util.List;

@Slf4j
public class WriterRepositoryImpl extends RepositoryBase<Writer, Long> implements WriterRepository {
    public WriterRepositoryImpl(Session session) {
        super(Writer.class, session);
    }
}
