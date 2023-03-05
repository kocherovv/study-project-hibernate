package org.example.service;

import org.example.domain.Post;
import org.example.domain.Writer;
import org.example.domain.enums.PostStatus;
import org.example.dto.WriterCreateDto;
import org.example.dto.WriterReadDto;
import org.example.dto.mapper.*;
import org.example.exception.NotFoundException;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WriterServiceTest {

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
        var writers = new ArrayList<Writer>();

        writers.add(Writer.builder()
            .id(1L)
            .lastName("asd")
            .firstName("asd")
            .build());

        writers.add(Writer.builder()
            .id(2L)
            .lastName("asd1")
            .firstName("asd1")
            .build());

        when(writerRepository.findAll()).thenReturn(writers);

        var expected = writers.stream().map(writerReadMapper::mapFrom).toList();

        var result = writerService.findAll();

        assertEquals(expected, result);
    }

    @Test
    void testFindById_Found() {

        var writer = Writer.builder()
            .id(1L)
            .firstName("123")
            .lastName("123")
            .build();

        var expectedPosts = new ArrayList<Post>();
        var post = Post.builder()
            .id(1L)
            .postStatus(PostStatus.ACTIVE)
            .writer(writer)
            .content("123")
            .created(LocalDateTime.now())
            .updated(LocalDateTime.now())
            .build();

        expectedPosts.add(post);

        writer.setPosts(expectedPosts);

        var expectedEntity = Optional.ofNullable(writer);

        when(writerRepository.findById(anyLong(), anyMap())).thenReturn(expectedEntity);

        var expectedResult = expectedEntity.map(writerReadMapper::mapFrom);

        var returnedDto = writerService.findById(14L, writerReadMapper, GraphPropertyName.POST_WITH_LABELS_WRITERS);

        assertEquals(expectedResult.get().getId(), returnedDto.get().getId());
        assertEquals(expectedResult.get().getFirstName(), returnedDto.get().getFirstName());
        assertEquals(expectedResult.get().getLastName(), returnedDto.get().getLastName());
        assertEquals(expectedResult.get().getPosts_id(), returnedDto.get().getPosts_id());
    }

    @Test
    void testFindById_Not_Found() {
        when(writerRepository.findById(anyLong(), anyMap())).thenReturn(Optional.empty());

        var expected = Optional.empty();

        var result = writerService.findById(1L, writerReadMapper, GraphPropertyName.POST_WITH_LABELS_WRITERS);

        assertEquals(expected, result);
    }

    @Test
    void testCreate() {

        var inputDto = WriterCreateDto.builder()
            .firstName("test")
            .lastName("test")
            .build();

        var inputEntity = Writer.builder()
            .firstName("test")
            .lastName("test")
            .build();

        var outputEntity = Writer.builder()
            .firstName("test")
            .lastName("test")
            .id(1L)
            .build();

        var expectedDto = writerReadMapper.mapFrom(outputEntity);

        when(writerRepository.create(inputEntity)).thenReturn(outputEntity);

        var result = writerService.create(inputDto);

        assertEquals(expectedDto, result);
    }

    @Test
    void testUpdate() {
        var inputDto = WriterReadDto.builder()
            .id(1L)
            .lastName("123")
            .firstName("123")
            .build();

        var oldEntity = Writer.builder()
            .id(1L)
            .lastName("test")
            .firstName("test")
            .build();

        var updatedEntity = Writer.builder()
            .id(1L)
            .lastName("123")
            .firstName("123")
            .build();

        var expectedDto = writerReadMapper.mapFrom(updatedEntity);

        when(writerRepository.findById(inputDto.getId())).thenReturn(Optional.ofNullable(oldEntity));
        when(writerRepository.update(updatedEntity)).thenReturn(updatedEntity);

        var result = writerService.update(inputDto);

        assertEquals(expectedDto.getId(), result.getId());
        assertEquals(expectedDto.getLastName(), result.getFirstName());
    }

    @Test
    void delete() {
        var writer = Writer.builder()
            .id(1L)
            .lastName("123")
            .firstName("123")
            .build();

        when(writerRepository.findById(anyLong())).thenReturn(Optional.ofNullable(writer));

        assertDoesNotThrow(() -> writerService.deleteById(1L));
    }

    @Test
    void delete_NotFound() {
        when(writerRepository.findById(anyLong()))
            .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> writerService.deleteById(1L));
    }
}