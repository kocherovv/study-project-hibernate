package org.example.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.domain.Writer;
import org.example.repository.RepositoryBase;
import org.example.repository.WriterRepository;

import javax.persistence.EntityManager;

@Slf4j
public class WriterRepositoryImpl extends RepositoryBase<Writer, Long> implements WriterRepository {
    public WriterRepositoryImpl(EntityManager entityManager) {
        super(Writer.class, entityManager);
    }
}
