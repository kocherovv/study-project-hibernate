package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.WriterCreateDto;
import org.example.dto.WriterReadDto;
import org.example.dto.mapper.WriterCreateMapper;
import org.example.dto.mapper.WriterReadMapper;
import org.example.repository.impl.WriterRepositoryImpl;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Transactional
public class WriterService {

    private final WriterRepositoryImpl writerRepositoryImpl;
    private final WriterReadMapper writerReadMapper;
    private final WriterCreateMapper writerCreateMapper;

    public List<WriterReadDto> findAll() {
        return writerRepositoryImpl.findAll().stream()
            .map(writerReadMapper::mapFrom)
            .toList();
    }

    public WriterReadDto findById(Long id) {
        return writerRepositoryImpl.findById(id)
            .map(writerReadMapper::mapFrom).orElse(null);
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

    public void deleteById(WriterReadDto writerReadDto) {
        var writer = writerRepositoryImpl.findById(writerReadDto.getId())
            .orElse(null);
        writerRepositoryImpl.delete(writer);
    }
}
