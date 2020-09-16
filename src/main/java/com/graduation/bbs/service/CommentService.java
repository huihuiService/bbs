package com.graduation.bbs.service;

import com.graduation.bbs.model.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> select(String topicId);

    int insert(Comment comment);
}
