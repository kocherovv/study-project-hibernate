package org.example.service;

import org.example.database.graphs.GraphPropertyName;
import org.example.domain.Label;
import org.example.domain.Post;
import org.example.domain.Writer;
import org.example.domain.enums.PostStatus;
import org.example.dto.PostCreateDto;
import org.example.dto.PostReadDto;
import org.example.exception.NotFoundException;
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

public class PostServiceTest extends AbstractTestBase {

    private Session session;

    @BeforeEach
    void init() {
        session = HibernateTestUtil.sessionFactory.getCurrentSession();

        session.beginTransaction();

        buildTestContainer(session);
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

        when(getPostRepository().findAll()).thenReturn(posts);

        var expected = posts.stream().map(getPostReadMapper()::mapFrom).toList();

        var result = getPostService().findAll();

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

        when(getPostRepository().findById(anyLong(), anyMap())).thenReturn(expectedPost);

        var expectedResult = expectedPost.map(getPostReadMapper()::mapFrom);

        var returnedDto = getPostService().findById(14L, getPostReadMapper(), GraphPropertyName.POST_WITH_LABELS_WRITERS);

        assertEquals(expectedResult.get().getId(), returnedDto.get().getId());
        assertEquals(expectedResult.get().getContent(), returnedDto.get().getContent());
        assertEquals(expectedResult.get().getLabelsDto(), returnedDto.get().getLabelsDto());
        assertEquals(expectedResult.get().getWriterReadDto(), returnedDto.get().getWriterReadDto());
        assertEquals(expectedResult.get().getPostStatus(), returnedDto.get().getPostStatus());
    }

    @Test
    void testFindById_Not_Found() {
        when(getPostRepository().findById(anyLong(), anyMap())).thenReturn(Optional.empty());

        var expected = Optional.empty();

        var result = getPostService().findById(1L, getPostReadMapper(), GraphPropertyName.POST_WITH_LABELS_WRITERS);

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

        var expectedDto = getPostReadMapper().mapFrom(outputEntity);

        when(getLabelRepository().findById(anyLong())).thenReturn(Optional.ofNullable(label));
        when(getWriterRepository().findById(anyLong())).thenReturn(Optional.ofNullable(writer));
        when(getPostRepository().create(inputEntity)).thenReturn(outputEntity);

        var result = getPostService().create(inputDto);

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

        var labelsDto = labels.stream().map(getLabelReadMapper()::mapFrom).toList();

        var writer = Writer.builder()
            .id(1L)
            .lastName("123")
            .firstName("123")
            .build();

        var writerDto = getWriterReadMapper().mapFrom(writer);

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

        var expectedDto = getPostReadMapper().mapFrom(updatedEntity);

        when(getWriterRepository().findById(inputDto.getWriterReadDto().getId())).thenReturn(Optional.of(writer));
        when(getPostRepository().findById(inputDto.getId())).thenReturn(Optional.ofNullable(oldEntity));
        when(getPostRepository().update(updatedEntity)).thenReturn(updatedEntity);

        var result = getPostService().update(inputDto);

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

        when(getPostRepository().findById(anyLong(), anyMap())).thenReturn(Optional.ofNullable(post));

        assertDoesNotThrow(() -> getPostService().deleteById(1L));
    }

    @Test
    void delete_NotFound() {
        when(getPostRepository().findById(anyLong()))
            .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> getPostService().deleteById(1L));
    }
}