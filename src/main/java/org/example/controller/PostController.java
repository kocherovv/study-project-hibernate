package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.dto.PostCreateDto;
import org.example.dto.PostReadDto;
import org.example.service.PostService;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class PostController {

    private final PostService postService;

    public List<PostReadDto> findAll() {
        return postService.findAll();
    }

    public Optional<PostReadDto> findById(Long id) {
        return postService.findById(id);
    }

    public void create(PostCreateDto postCreateDto) {
        postService.create(postCreateDto);
    }

    public void update(PostReadDto postReadDto) {
        postService.update(postReadDto);
    }

    public void deleteById(Long id) {
        postService.deleteById(id);
    }
}
