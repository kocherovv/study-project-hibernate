package org.example.repository.impl;

import lombok.extern.log4j.Log4j;
import org.example.domain.Writer;
import org.example.repository.RepositoryBase;
import org.example.repository.WriterRepository;

import javax.persistence.EntityManager;

@Log4j
public class WriterRepositoryImpl extends RepositoryBase<Writer, Long> implements WriterRepository {
    public WriterRepositoryImpl(EntityManager entityManager) {
        super(Writer.class, entityManager);
    }
}
