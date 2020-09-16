package com.graduation.bbs.service;

import com.graduation.bbs.common.Pagination;
import com.graduation.bbs.model.Blog;

import java.util.List;

public interface BlogService {

    Pagination<Blog> selectAll(Integer currPage, Integer pageSize);

    Pagination<Blog> selectAll(String title, Integer currPage, Integer pageSize);

    List<Blog> selectAllByUpdateDesc(Integer currPage, Integer pageSize);

    int insert(Blog record);

    Blog selectByBid(String bid);

    int update(Blog record);

    int delete(Integer id);

}
