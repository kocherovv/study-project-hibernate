package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.example.domain.Post;
import org.example.dto.PostCreateDto;
import org.example.dto.PostReadDto;
import org.example.dto.mapper.Mapper;
import org.example.dto.mapper.PostCreateMapper;
import org.example.dto.mapper.PostReadMapper;
import org.example.exception.NotFoundException;
import org.example.graphs.GraphPropertyBuilder;
import org.example.graphs.GraphPropertyName;
import org.example.model.AppStatusCode;
import org.example.repository.impl.LabelRepositoryImpl;
import org.example.repository.impl.PostRepositoryImpl;
import org.example.repository.impl.WriterRepositoryImpl;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Log4j
@Transactional
public class PostService {

    private final LabelRepositoryImpl labelRepositoryImpl;

    private final PostRepositoryImpl postRepositoryImpl;

    private final WriterRepositoryImpl writerRepositoryImpl;

    private final PostReadMapper postReadMapper;

    private final PostCreateMapper postCreateMapper;

    private final GraphPropertyBuilder graphPropertyBuilder;

    public List<PostReadDto> findAll() {
        return postRepositoryImpl.findAll().stream()
            .map(postReadMapper::mapFrom)
            .toList();
    }

    public Optional<PostReadDto> findById(Long id) {
        return postRepositoryImpl.findById(id)
            .map(postReadMapper::mapFrom);
    }

    public <T> Optional<T> findById(Long id, Mapper<Post, T> mapper) {
        return postRepositoryImpl.findById(id)
            .map(mapper::mapFrom);
    }

    public <T> Optional<T> findById(Long id, Mapper<Post, T> mapper, GraphPropertyName graphPropertyName) {
        return postRepositoryImpl.findById(id, graphPropertyBuilder.getProperty(graphPropertyName))
            .map(mapper::mapFrom);
    }

    public PostReadDto create(PostCreateDto postReadDto) {
        return postReadMapper.mapFrom(postRepositoryImpl.create(postCreateMapper.mapFrom(postReadDto)));
    }

    public PostReadDto update(PostReadDto postReadDto) {
        var post = postRepositoryImpl.findById(postReadDto.getId())
            .orElseThrow(NotFoundException::new);

        post.setPostStatus(postReadDto.getPostStatus());
        post.setContent(postReadDto.getContent());
        post.setWriter(writerRepositoryImpl.findById(postReadDto.getWriterReadDto().getId())
            .orElseThrow(NotFoundException::new));

        return postReadMapper.mapFrom(postRepositoryImpl.update(post));
    }

    public void deleteById(Long id) throws NotFoundException {

        var postToDelete = postRepositoryImpl.findById(id, graphPropertyBuilder
            .getProperty(GraphPropertyName.POST_WITH_LABELS));

        if (postToDelete.isPresent()) {
            postRepositoryImpl.delete(postToDelete.get());

            var labelList = postToDelete.get().getLabels();

            labelList.forEach(label -> {
                if (label.getPosts().size() == 0) {
                    labelRepositoryImpl.delete(label);
                }
            });
        } else {
            throw new NotFoundException(AppStatusCode.NOT_FOUND_EXCEPTION);
        }
    }
}
