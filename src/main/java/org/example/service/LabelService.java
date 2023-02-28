package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.Label;
import org.example.dto.LabelCreateDto;
import org.example.dto.LabelReadDto;
import org.example.dto.LabelReadCollectionsDto;
import org.example.dto.mapper.LabelCreateMapper;
import org.example.dto.mapper.LabelReadMapper;
import org.example.dto.mapper.LabelUpdateMapper;
import org.example.dto.mapper.Mapper;
import org.example.repository.impl.LabelRepositoryImpl;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Transactional
public class LabelService {

    private final LabelRepositoryImpl labelRepositoryImpl;

    private final LabelCreateMapper labelCreateMapper;

    private final LabelReadMapper labelReadMapper;

    private final LabelUpdateMapper labelUpdateMapper;

    public List<LabelReadDto> findAll() {
        return labelRepositoryImpl.findAll().stream()
            .map(labelReadMapper::mapFrom)
            .toList();
    }

    public Optional<LabelReadDto> findById(Long id) {
        return labelRepositoryImpl.findById(id)
            .map(labelReadMapper::mapFrom);
    }

    public <T> Optional<T> findById(Long id, Mapper<Label, T> mapper) {
        return labelRepositoryImpl.findById(id)
            .map(mapper::mapFrom);
    }

    public LabelReadCollectionsDto create(LabelCreateDto labelCreateDto) {
        var label = labelRepositoryImpl.create(labelCreateMapper.mapFrom(labelCreateDto));

        return labelUpdateMapper.mapFrom(label);
    }

    public LabelReadDto update(LabelReadDto labelReadDto) {
        var label = labelRepositoryImpl.findById(labelReadDto.getId())
            .orElseThrow(IllegalArgumentException::new);

        label.setName(labelReadDto.getName());

        labelRepositoryImpl.update(label);

        return labelReadDto;
    }

    public void deleteById(Long id) {
        labelRepositoryImpl.delete(labelRepositoryImpl.findById(id).orElse(null));
    }

    public Optional<LabelReadDto> findByName(String name) {
        return labelRepositoryImpl.findByName(name)
            .map(labelReadMapper::mapFrom);
    }
}
