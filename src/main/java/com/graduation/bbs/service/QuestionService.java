package com.graduation.bbs.service;

import com.github.pagehelper.page.PageMethod;
import com.graduation.bbs.common.Pagination;
import com.graduation.bbs.model.Question;
import com.graduation.bbs.model.QuestionExample;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface QuestionService {

    Pagination<Question> selectAll(String title, Integer currPage, Integer pageSize);


    Pagination<Question> selectAll(Integer currPage, Integer pageSize);

    List<Question> selectAllByUpdateDesc(Integer currPage, Integer pageSize);

    int insert(Question record);

    Question selectByQid(String qid);

    int update(Question record);

    int delete(Integer id);
}
