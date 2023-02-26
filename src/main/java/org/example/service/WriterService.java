package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.WriterDto;
import org.example.dto.mapper.PostDtoMapper;
import org.example.dto.mapper.WriterDtoMapper;
import org.example.dto.mapper.WriterMapper;
import org.example.repository.impl.PostRepositoryImpl;
import org.example.repository.impl.WriterRepositoryImpl;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class WriterService {

    private final WriterRepositoryImpl writerRepositoryImpl;
    private final PostRepositoryImpl postRepositoryImpl;
    private final PostDtoMapper postDtoMapper;
    private final WriterDtoMapper writerDtoMapper;
    private final WriterMapper writerMapper;

    public List<WriterDto> findAll() {
        return writerRepositoryImpl.findAll().stream()
            .map(writerDtoMapper::map)
            .toList();
    }

    public WriterDto findById(Long id) {
        return writerRepositoryImpl.findById(id)
            .map(writerDtoMapper::map).orElse(null);
    }

    public WriterDto create(WriterDto writerDto) {
        return writerDtoMapper.map(writerRepositoryImpl.create(writerMapper.map(writerDto)));
    }

    public WriterDto update(WriterDto writerDto) {
        writerRepositoryImpl.update(writerMapper.map(writerDto));

        log.info("{} - updated.", writerDto);

        return writerDto;
    }

    public void deleteById(WriterDto writer) {
        writerRepositoryImpl.delete(writerMapper.map(writer));
    }
}
