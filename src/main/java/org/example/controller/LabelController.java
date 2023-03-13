package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.LabelCreateDto;
import org.example.dto.LabelReadDto;
import org.example.dto.LabelUpdateDto;
import org.example.service.LabelService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class LabelController {

    private final LabelService labelService;

    public List<LabelReadDto> findAll() {
        return labelService.findAll();
    }

    public Optional<LabelReadDto> findById(Long id) {
        return labelService.findById(id);
    }

    public LabelUpdateDto create(LabelCreateDto newLabelDto) {
        return labelService.create(newLabelDto);
    }

    public void update(LabelUpdateDto labelUpdateDto) {
        labelService.update(labelUpdateDto);
    }

    public void deleteById(Long id) {
        labelService.deleteById(id);
    }

    public Optional<LabelReadDto> findByName(String name) {
        return labelService.findByName(name);
    }
}
