package org.example.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.domain.Label;
import org.example.domain.Post;
import org.example.domain.enums.PostStatus;
import org.example.exception.AppException;
import org.example.model.AppStatusCode;
import org.example.repository.PostRepository;
import org.example.repository.RepositoryBase;
import org.hibernate.Session;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class PostRepositoryImpl extends RepositoryBase<Post, Long> implements PostRepository {


    public PostRepositoryImpl(Session session) {
        super(Post.class, session);
    }

    @Override
    public List<Post> findAllByLabelId(Long labelId) {
        var sql = "SELECT Post.* FROM Post JOIN PostLabel ON PostLabel.post_id = Post.id WHERE PostLabel.label_id = ?";
        return null;
    }

    @Override
    public List<Post> findAllByWriterId(Long writerId) {
        var sql = "SELECT Post.* FROM Post JOIN Writer ON Writer.id = Post.writer_id WHERE Writer.id = ?";
        return null;
    }
}
