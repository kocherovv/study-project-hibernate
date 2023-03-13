package org.example.database.repository.impl;

import lombok.extern.log4j.Log4j;
import org.example.database.repository.RepositoryBase;
import org.example.database.repository.WriterRepository;
import org.example.domain.Writer;

import javax.persistence.EntityManager;

@Log4j
public class WriterRepositoryImpl extends RepositoryBase<Writer, Long> implements WriterRepository {
    public WriterRepositoryImpl(EntityManager entityManager) {
        super(Writer.class, entityManager);
    }
}
