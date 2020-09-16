package com.graduation.bbs.service.impl;

import com.graduation.bbs.dao.CommentMapper;
import com.graduation.bbs.model.Comment;
import com.graduation.bbs.model.CommentExample;
import com.graduation.bbs.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<Comment> select(String topicId) {
        CommentExample example = new CommentExample();
        CommentExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause(" GMT_CREATE DESC ");
        criteria.andTopicIdEqualTo(topicId);
        List<Comment> commentList = commentMapper.selectByExampleWithBLOBs(example);
        return commentList;
    }

    @Override
    public int insert(Comment comment) {
        return commentMapper.insert(comment);
    }


}
