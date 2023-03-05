package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.Writer;
import org.example.dto.WriterCreateDto;
import org.example.dto.WriterReadDto;
import org.example.dto.mapper.Mapper;
import org.example.dto.mapper.WriterCreateMapper;
import org.example.dto.mapper.WriterReadMapper;
import org.example.exception.NotFoundException;
import org.example.graphs.GraphPropertyBuilder;
import org.example.graphs.GraphPropertyName;
import org.example.model.AppStatusCode;
import org.example.repository.impl.WriterRepositoryImpl;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Log4j
@Transactional
public class WriterService {

    private final WriterRepositoryImpl writerRepositoryImpl;
    private final WriterReadMapper writerReadMapper;
    private final WriterCreateMapper writerCreateMapper;
    private final GraphPropertyBuilder graphPropertyBuilder;

    public List<WriterReadDto> findAll() {
        return writerRepositoryImpl.findAll().stream()
            .map(writerReadMapper::mapFrom)
            .toList();
    }

    public Optional<WriterReadDto> findById(Long id) {
        return writerRepositoryImpl.findById(id)
            .map(writerReadMapper::mapFrom);
    }

    public <T> Optional<T> findById(Long id, Mapper<Writer, T> mapper) {
        return writerRepositoryImpl.findById(id)
            .map(mapper::mapFrom);
    }

    public <T> Optional<T> findById(Long id, Mapper<Writer, T> mapper, GraphPropertyName graphPropertyName) {
        return writerRepositoryImpl.findById(id, graphPropertyBuilder.getProperty(graphPropertyName))
            .map(mapper::mapFrom);
    }

    public WriterReadDto create(WriterCreateDto writerCreateDto) {
        return writerReadMapper.mapFrom(writerRepositoryImpl.create(writerCreateMapper.mapFrom(writerCreateDto)));
    }

    public WriterReadDto update(WriterReadDto writerReadDto) {
        var writer = writerRepositoryImpl.findById(writerReadDto.getId())
            .orElseThrow(IllegalArgumentException::new);

        writer.setFirstName(writerReadDto.getFirstName());
        writer.setLastName(writerReadDto.getLastName());

        writerRepositoryImpl.update(writer);

        return writerReadDto;
    }

    public void deleteById(Long id) throws NotFoundException {
        var writer = writerRepositoryImpl.findById(id);

        if (writer.isPresent()) {
            writerRepositoryImpl.delete(writer.get());
        } else {
            throw new NotFoundException(AppStatusCode.NOT_FOUND_EXCEPTION);
        }
    }
}
