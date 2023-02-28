package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.dto.LabelCreateDto;
import org.example.dto.LabelReadDto;
import org.example.dto.LabelReadCollectionsDto;
import org.example.service.LabelService;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class LabelController {

    private final LabelService labelService;

    public List<LabelReadDto> findAll() {
        return labelService.findAll();
    }

    public Optional<LabelReadDto> findById(Long id) {
        return labelService.findById(id);
    }

    public LabelReadCollectionsDto create(LabelCreateDto newLabelDto) {
        return labelService.create(newLabelDto);
    }

    public void update(LabelReadDto labelReadDto) {
        labelService.update(labelReadDto);
    }

    public void deleteById(Long id) {
        labelService.deleteById(id);
    }

    public Optional<LabelReadDto> findByName(String name) {
        return labelService.findByName(name);
    }
}
