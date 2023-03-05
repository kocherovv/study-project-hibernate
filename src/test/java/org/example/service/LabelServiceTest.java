package org.example.service;

import org.example.domain.Label;
import org.example.domain.Post;
import org.example.domain.enums.PostStatus;
import org.example.dto.LabelCreateDto;
import org.example.dto.LabelReadDto;
import org.example.dto.LabelUpdateDto;
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

public class LabelServiceTest {

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
        var labels = new ArrayList<Label>();
        labels.add(Label.builder()
            .id(1L)
            .name("123")
            .build());

        labels.add(Label.builder()
            .id(2L)
            .name("222")
            .build());

        when(labelRepository.findAll()).thenReturn(labels);

        var expected = labels.stream().map(labelReadMapper::mapFrom).toList();

        var result = labelService.findAll();

        assertEquals(expected, result);
    }

    @Test
    void testFindById_Found() {

        var label = Label.builder()
            .id(1L)
            .name("123")
            .build();

        var expectedPosts = new ArrayList<Post>();
        var post = Post.builder()
            .id(1L)
            .postStatus(PostStatus.ACTIVE)
            .updated(LocalDateTime.now())
            .created(LocalDateTime.now())
            .content("asd")
            .build();
        expectedPosts.add(post);

        label.setPosts(expectedPosts);

        var expectedLabel = Optional.ofNullable(label);

        when(labelRepository.findById(anyLong(), anyMap())).thenReturn(expectedLabel);

        var expectedResult = expectedLabel.map(labelUpdateMapper::mapFrom);

        var returnedDto = labelService.findById(14L, labelUpdateMapper, GraphPropertyName.LABEL_WITH_POSTS);

        System.out.println();

        assertEquals(expectedResult.get().getId(), returnedDto.get().getId());
        assertEquals(expectedResult.get().getName(), returnedDto.get().getName());
        assertEquals(expectedResult.get().getPosts_id(), returnedDto.get().getPosts_id());
    }

    @Test
    void testFindById_Not_Found() {
        when(labelRepository.findById(anyLong(), anyMap())).thenReturn(Optional.empty());

        var expected = Optional.empty();

        var result = labelService.findById(1L, labelUpdateMapper, GraphPropertyName.LABEL_WITH_POSTS);

        assertEquals(expected, result);
    }

    @Test
    void testCreate() {
        var posts = new ArrayList<Post>();

        var post = Post.builder()
            .content("test")
            .created(LocalDateTime.now())
            .updated(LocalDateTime.now())
            .id(1L)
            .postStatus(PostStatus.ACTIVE)
            .build();

        posts.add(post);

        var postsId = posts.stream().map(Post::getId).toList();

        var inputDto = LabelCreateDto.builder()
            .name("Anastasia")
            .posts_id(postsId)
            .build();

        var inputEntity = Label.builder()
            .name("Anastasia")
            .posts(posts)
            .build();

        var outputEntity = Label.builder()
            .id(1L)
            .name("Anastasia")
            .posts(posts)
            .build();

        var expectedDto = LabelUpdateDto.builder()
            .id(1L)
            .name("Anastasia")
            .posts_id(postsId)
            .build();

        when(postRepository.findById(anyLong())).thenReturn(Optional.ofNullable(post));
        when(labelRepository.create(inputEntity)).thenReturn(outputEntity);

        var result = labelService.create(inputDto);

        assertEquals(expectedDto, result);
    }

    @Test
    void testUpdate_UpdateDto() {
        var posts = new ArrayList<Post>();

        var post = Post.builder()
            .content("test")
            .created(LocalDateTime.now())
            .updated(LocalDateTime.now())
            .id(1L)
            .postStatus(PostStatus.ACTIVE)
            .build();

        posts.add(post);

        var postsId = posts.stream().map(Post::getId).toList();

        var inputDto = LabelUpdateDto.builder()
            .id(1L)
            .name("Anastasia")
            .posts_id(postsId)
            .build();

        var oldEntity = Label.builder()
            .id(1L)
            .name("Dima")
            .posts(posts)
            .build();

        var updatedEntity = Label.builder()
            .id(1L)
            .name("Anastasia")
            .posts(posts)
            .build();

        var expectedDto = LabelUpdateDto.builder()
            .id(1L)
            .name("Anastasia")
            .posts_id(postsId)
            .build();

        when(labelRepository.findById(updatedEntity.getId())).thenReturn(Optional.ofNullable(oldEntity));
        when(labelRepository.update(updatedEntity)).thenReturn(updatedEntity);

        var result = labelService.update(inputDto);

        assertEquals(expectedDto, result);
    }

    @Test
    void delete_Found() {
        when(labelRepository.findById(anyLong()))
            .thenReturn(Optional.ofNullable(Label.builder()
                .id(1L)
                .name("123")
                .build()));

        assertDoesNotThrow(() -> labelService.deleteById(1L));
    }

    @Test
    void delete_NotFound() {
        when(labelRepository.findById(anyLong()))
            .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> labelService.deleteById(anyLong()));
    }

    @Test
    void testFindByName_Found() {
        var label = Label.builder()
            .id(1L)
            .name("123")
            .build();

        var expectedLabel = Optional.ofNullable(label);

        when(labelRepository.findByName(anyString())).thenReturn(expectedLabel);

        var expectedResult = expectedLabel.map(labelReadMapper::mapFrom);

        var returnedDto = labelService.findByName("test");

        System.out.println();

        assertEquals(expectedResult.get().getId(), returnedDto.get().getId());
        assertEquals(expectedResult.get().getName(), returnedDto.get().getName());
    }

    @Test
    void testFindByName_Not_Found() {
        when(labelRepository.findByName(anyString())).thenReturn(Optional.empty());

        var expected = Optional.empty();

        var result = labelService.findByName("test");

        assertEquals(expected, result);
    }
}
