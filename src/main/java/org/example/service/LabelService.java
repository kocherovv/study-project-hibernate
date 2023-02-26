package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.LabelDto;
import org.example.dto.mapper.LabelDtoMapper;
import org.example.dto.mapper.LabelMapper;
import org.example.dto.mapper.PostDtoMapper;
import org.example.repository.impl.LabelRepositoryImpl;
import org.example.repository.impl.PostRepositoryImpl;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class LabelService {

    private final LabelRepositoryImpl labelRepositoryImpl;
    private final PostRepositoryImpl postRepositoryImpl;
    private final LabelMapper labelMapper;
    private final LabelDtoMapper labelDtoMapper;
    private final PostDtoMapper postDtoMapper;

    public List<LabelDto> findAll() {
        return labelRepositoryImpl.findAll().stream()
            .map(labelDtoMapper::map)
            .toList();
    }

    public LabelDto findById(Long id) {
        return labelRepositoryImpl.findById(id)
            .map(labelDtoMapper::map)
            .orElse(null);
    }

    public LabelDto create(LabelDto newLabelDto) {
        return labelDtoMapper.map(labelRepositoryImpl.create(labelMapper.map(newLabelDto)));
    }

    public LabelDto update(LabelDto labelDto) {
        return labelDtoMapper.map(labelRepositoryImpl.update(labelMapper.map(labelDto)));
    }

    public void deleteById(LabelDto id) {
        labelRepositoryImpl.delete(labelMapper.map(id));
    }

    public LabelDto findByName(String name) {
        return labelRepositoryImpl.findByName(name)
            .map(labelDtoMapper::map)
            .orElse(null);
    }
}
