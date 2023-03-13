package org.example.service;

import org.example.database.graphs.GraphPropertyName;
import org.example.domain.Label;
import org.example.domain.Post;
import org.example.domain.enums.PostStatus;
import org.example.dto.LabelCreateDto;
import org.example.dto.LabelUpdateDto;
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

public class LabelServiceTest extends AbstractTestBase {

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
        var labels = new ArrayList<Label>();
        labels.add(Label.builder()
            .id(1L)
            .name("123")
            .build());

        labels.add(Label.builder()
            .id(2L)
            .name("222")
            .build());

        when(getLabelRepository().findAll()).thenReturn(labels);

        var expected = labels.stream().map(getLabelReadMapper()::mapFrom).toList();

        var result = getLabelService().findAll();

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

        when(getLabelRepository().findById(anyLong(), anyMap())).thenReturn(expectedLabel);

        var expectedResult = expectedLabel.map(getLabelUpdateMapper()::mapFrom);

        var returnedDto = getLabelService().findById(14L, getLabelUpdateMapper(), GraphPropertyName.LABEL_WITH_POSTS);

        System.out.println();

        assertEquals(expectedResult.get().getId(), returnedDto.get().getId());
        assertEquals(expectedResult.get().getName(), returnedDto.get().getName());
        assertEquals(expectedResult.get().getPosts_id(), returnedDto.get().getPosts_id());
    }

    @Test
    void testFindById_Not_Found() {
        when(getLabelRepository().findById(anyLong(), anyMap())).thenReturn(Optional.empty());

        var expected = Optional.empty();

        var result = getLabelService().findById(1L, getLabelUpdateMapper(), GraphPropertyName.LABEL_WITH_POSTS);

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

        when(getPostRepository().findById(anyLong())).thenReturn(Optional.ofNullable(post));
        when(getLabelRepository().create(inputEntity)).thenReturn(outputEntity);

        var result = getLabelService().create(inputDto);

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

        when(getLabelRepository().findById(updatedEntity.getId())).thenReturn(Optional.ofNullable(oldEntity));
        when(getLabelRepository().update(updatedEntity)).thenReturn(updatedEntity);

        var result = getLabelService().update(inputDto);

        assertEquals(expectedDto, result);
    }

    @Test
    void delete_Found() {
        when(getLabelRepository().findById(anyLong()))
            .thenReturn(Optional.ofNullable(Label.builder()
                .id(1L)
                .name("123")
                .build()));

        assertDoesNotThrow(() -> getLabelService().deleteById(1L));
    }

    @Test
    void delete_NotFound() {
        when(getLabelRepository().findById(anyLong()))
            .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> getLabelService().deleteById(anyLong()));
    }

    @Test
    void testFindByName_Found() {
        var label = Label.builder()
            .id(1L)
            .name("123")
            .build();

        var expectedLabel = Optional.ofNullable(label);

        when(getLabelRepository().findByName(anyString())).thenReturn(expectedLabel);

        var expectedResult = expectedLabel.map(getLabelReadMapper()::mapFrom);

        var returnedDto = getLabelService().findByName("test");

        System.out.println();

        assertEquals(expectedResult.get().getId(), returnedDto.get().getId());
        assertEquals(expectedResult.get().getName(), returnedDto.get().getName());
    }

    @Test
    void testFindByName_Not_Found() {
        when(getLabelRepository().findByName(anyString())).thenReturn(Optional.empty());

        var expected = Optional.empty();

        var result = getLabelService().findByName("test");

        assertEquals(expected, result);
    }
}
