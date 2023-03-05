package org.example.service;

import org.example.domain.Label;
import org.example.domain.Post;
import org.example.domain.Writer;
import org.example.domain.enums.PostStatus;
import org.example.dto.PostCreateDto;
import org.example.dto.PostReadDto;
import org.example.dto.WriterReadDto;
import org.example.dto.mapper.*;
import org.example.graphs.GraphPropertyBuilder;
import org.example.graphs.GraphPropertyName;
import org.example.repository.impl.LabelRepositoryImpl;
import org.example.repository.impl.PostRepositoryImpl;
import org.example.repository.impl.WriterRepositoryImpl;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.HibernateTestUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PostServiceTest {

    private Session session;

    private PostRepositoryImpl postRepository;
    private LabelRepositoryImpl labelRepository;
    private WriterRepositoryImpl writerRepository;
    private LabelCreateMapper labelCreateMapper;
    private LabelUpdateMapper labelUpdateMapper;
    private LabelReadMapper labelReadMapper;
    private WriterReadMapper writerReadMapper;
    private WriterCreateMapper writerCreateMapper;
    private PostCreateMapper postCreateMapper;
    private PostReadMapper postReadMapper;
    private LabelService labelService;
    private PostService postService;
    private WriterService writerService;
    private GraphPropertyBuilder graphPropertyBuilder;

    @BeforeEach
    void init() {
        session = HibernateTestUtil.sessionFactory.getCurrentSession();

        session.beginTransaction();

        postRepository = mock(PostRepositoryImpl.class);
        labelRepository = mock(LabelRepositoryImpl.class);
        writerRepository = mock(WriterRepositoryImpl.class);

        labelCreateMapper = new LabelCreateMapper(postRepository);
        labelUpdateMapper = new LabelUpdateMapper();
        labelReadMapper = new LabelReadMapper();
        writerReadMapper = new WriterReadMapper();
        writerCreateMapper = new WriterCreateMapper();
        postCreateMapper = new PostCreateMapper(labelRepository, writerRepository);
        postReadMapper = new PostReadMapper(writerReadMapper, labelReadMapper);
        graphPropertyBuilder = new GraphPropertyBuilder(session);
        labelService = new LabelService(labelRepository, labelCreateMapper, labelReadMapper, labelUpdateMapper, graphPropertyBuilder);
        postService = new PostService(labelRepository, postRepository, writerRepository, postReadMapper, postCreateMapper, graphPropertyBuilder);
        writerService = new WriterService(writerRepository, writerReadMapper, writerCreateMapper, graphPropertyBuilder);
    }

    @AfterEach
    void close() {
        session.getTransaction().commit();
    }

    @Test
    void testFindAll() {
        var posts = new ArrayList<Post>();

        posts.add(Post.builder()
            .id(1L)
            .writer(Writer.builder()
                .firstName("asd")
                .lastName("123")
                .id(1L)
                .build())
            .postStatus(PostStatus.ACTIVE)
            .updated(LocalDateTime.now())
            .created(LocalDateTime.now())
            .content("asd")
            .build());

        posts.add(Post.builder()
            .id(2L)
            .writer(Writer.builder()
                .firstName("11111")
                .lastName("1s23")
                .id(2L)
                .build())
            .postStatus(PostStatus.ACTIVE)
            .updated(LocalDateTime.now())
            .created(LocalDateTime.now())
            .content("asd")
            .build());

        when(postRepository.findAll()).thenReturn(posts);

        var expected = posts.stream().map(postReadMapper::mapFrom).toList();

        var result = postService.findAll();

        assertEquals(expected, result);
    }

    @Test
    void testFindById_Found() {

        var post = Post.builder()
            .id(1L)
            .writer(Writer.builder()
                .firstName("asd")
                .lastName("123")
                .id(1L)
                .build())
            .postStatus(PostStatus.ACTIVE)
            .updated(LocalDateTime.now())
            .created(LocalDateTime.now())
            .content("asd")
            .build();

        var expectedLabels = new ArrayList<Label>();
        var label = Label.builder()
            .id(1L)
            .name("123")
            .build();

        expectedLabels.add(label);

        post.setLabels(expectedLabels);

        var expectedPost = Optional.ofNullable(post);

        when(postRepository.findById(anyLong(), anyMap())).thenReturn(expectedPost);

        var expectedResult = expectedPost.map(postReadMapper::mapFrom);

        var returnedDto = postService.findById(14L, postReadMapper, GraphPropertyName.POST_WITH_LABELS_WRITERS);

        assertEquals(expectedResult.get().getId(), returnedDto.get().getId());
        assertEquals(expectedResult.get().getContent(), returnedDto.get().getContent());
        assertEquals(expectedResult.get().getLabelsDto(), returnedDto.get().getLabelsDto());
        assertEquals(expectedResult.get().getWriterReadDto(), returnedDto.get().getWriterReadDto());
        assertEquals(expectedResult.get().getPostStatus(), returnedDto.get().getPostStatus());
    }

    @Test
    void testFindById_Not_Found() {
        when(postRepository.findById(anyLong(), anyMap())).thenReturn(Optional.empty());

        var expected = Optional.empty();

        var result = postService.findById(1L, postReadMapper, GraphPropertyName.POST_WITH_LABELS_WRITERS);

        assertEquals(expected, result);
    }

    @Test
    void testCreate() {
        var labels = new ArrayList<Label>();

        var label = Label.builder()
            .id(1L)
            .name("123")
            .build();

        labels.add(label);

        var labelsId = labels.stream().map(Label::getId).toList();

        var writer = Writer.builder()
            .firstName("test")
            .lastName("test")
            .id(1L)
            .build();

        var inputDto = PostCreateDto.builder()
            .writerId(1L)
            .labels_id(labelsId)
            .content("test")
            .build();

        var inputEntity = Post.builder()
            .writer(writer)
            .labels(labels)
            .content("test")
            .build();

        var outputEntity = Post.builder()
            .id(1L)
            .writer(writer)
            .labels(labels)
            .postStatus(PostStatus.ACTIVE)
            .updated(LocalDateTime.now())
            .created(LocalDateTime.now())
            .content("test")
            .build();

        var expectedDto = postReadMapper.mapFrom(outputEntity);

        when(labelRepository.findById(anyLong())).thenReturn(Optional.ofNullable(label));
        when(writerRepository.findById(anyLong())).thenReturn(Optional.ofNullable(writer));
        when(postRepository.create(inputEntity)).thenReturn(outputEntity);

        var result = postService.create(inputDto);

        assertEquals(expectedDto, result);
    }

    @Test
    void testUpdate() {
        var labels = new ArrayList<Label>();

        var label = Label.builder()
            .id(1L)
            .name("Dima")
            .build();

        labels.add(label);

        var labelsDto = labels.stream().map(labelReadMapper::mapFrom).toList();

        var writer = Writer.builder()
            .id(1L)
            .lastName("123")
            .firstName("123")
            .build();

        var writerDto = writerReadMapper.mapFrom(writer);

        var inputDto = PostReadDto.builder()
            .id(1L)
            .writerReadDto(writerDto)
            .labelsDto(labelsDto)
            .content("test")
            .build();

        var oldEntity = Post.builder()
            .id(1L)
            .writer(writer)
            .labels(labels)
            .postStatus(PostStatus.ACTIVE)
            .updated(LocalDateTime.now())
            .created(LocalDateTime.of(2023, 8, 8, 12, 12, 12, 4312))
            .content("OldEntity")
            .build();

        var updatedEntity = Post.builder()
            .id(1L)
            .writer(writer)
            .labels(labels)
            .postStatus(PostStatus.ACTIVE)
            .updated(LocalDateTime.now())
            .created(LocalDateTime.of(2023, 8, 8, 12, 12, 12, 4312))
            .content("test")
            .build();

        var expectedDto = postReadMapper.mapFrom(updatedEntity);

        when(writerRepository.findById(inputDto.getWriterReadDto().getId())).thenReturn(Optional.of(writer));
        when(postRepository.findById(inputDto.getId())).thenReturn(Optional.ofNullable(oldEntity));
        when(postRepository.update(updatedEntity)).thenReturn(updatedEntity);

        var result = postService.update(inputDto);

        assertEquals(expectedDto.getId(), result.getId());
        assertEquals(expectedDto.getCreated(), result.getCreated());
        assertEquals(expectedDto.getLabelsDto(), result.getLabelsDto());
        assertEquals(expectedDto.getContent(), result.getContent());
        assertEquals(expectedDto.getWriterReadDto(), result.getWriterReadDto());
        assertEquals(expectedDto.getPostStatus(), result.getPostStatus());
    }

    @Test
    void delete() {
        var labels = new ArrayList<Label>();

        var label = Label.builder()
            .name("test")
            .id(1L)
            .build();

        labels.add(label);

        var post = Post.builder()
            .id(1L)
            .labels(labels)
            .postStatus(PostStatus.ACTIVE)
            .updated(LocalDateTime.now())
            .created(LocalDateTime.of(2023, 8, 8, 12, 12, 12, 4312))
            .content("OldEntity")
            .build();

        post.setLabels(labels);

        when(postRepository.findById(anyLong(), anyMap())).thenReturn(Optional.ofNullable(post));

        assertDoesNotThrow(() -> postService.deleteById(1L));
    }
}