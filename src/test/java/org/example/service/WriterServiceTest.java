package org.example.service;

import org.example.database.graphs.GraphPropertyName;
import org.example.domain.Post;
import org.example.domain.Writer;
import org.example.domain.enums.PostStatus;
import org.example.dto.WriterCreateDto;
import org.example.dto.WriterReadDto;
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

public class WriterServiceTest extends AbstractTestBase {

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

        when(getWriterRepository().findAll()).thenReturn(writers);

        var expected = writers.stream().map(getWriterReadMapper()::mapFrom).toList();

        var result = getWriterService().findAll();

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

        when(getWriterRepository().findById(anyLong(), anyMap())).thenReturn(expectedEntity);

        var expectedResult = expectedEntity.map(getWriterReadMapper()::mapFrom);

        var returnedDto = getWriterService().findById(14L, getWriterReadMapper(), GraphPropertyName.POST_WITH_LABELS_WRITERS);

        assertEquals(expectedResult.get().getId(), returnedDto.get().getId());
        assertEquals(expectedResult.get().getFirstName(), returnedDto.get().getFirstName());
        assertEquals(expectedResult.get().getLastName(), returnedDto.get().getLastName());
        assertEquals(expectedResult.get().getPosts_id(), returnedDto.get().getPosts_id());
    }

    @Test
    void testFindById_Not_Found() {
        when(getWriterRepository().findById(anyLong(), anyMap())).thenReturn(Optional.empty());

        var expected = Optional.empty();

        var result = getWriterService().findById(1L, getWriterReadMapper(), GraphPropertyName.POST_WITH_LABELS_WRITERS);

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

        var expectedDto = getWriterReadMapper().mapFrom(outputEntity);

        when(getWriterRepository().create(inputEntity)).thenReturn(outputEntity);

        var result = getWriterService().create(inputDto);

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

        var expectedDto = getWriterReadMapper().mapFrom(updatedEntity);

        when(getWriterRepository().findById(inputDto.getId())).thenReturn(Optional.ofNullable(oldEntity));
        when(getWriterRepository().update(updatedEntity)).thenReturn(updatedEntity);

        var result = getWriterService().update(inputDto);

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

        when(getWriterRepository().findById(anyLong())).thenReturn(Optional.ofNullable(writer));

        assertDoesNotThrow(() -> getWriterService().deleteById(1L));
    }

    @Test
    void delete_NotFound() {
        when(getWriterRepository().findById(anyLong()))
            .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> getWriterService().deleteById(1L));
    }
}