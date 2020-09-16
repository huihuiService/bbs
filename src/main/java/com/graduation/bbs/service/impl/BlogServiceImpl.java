package com.graduation.bbs.service.impl;

import com.github.pagehelper.page.PageMethod;
import com.graduation.bbs.common.Pagination;
import com.graduation.bbs.dao.BlogMapper;
import com.graduation.bbs.model.Blog;
import com.graduation.bbs.model.BlogExample;
import com.graduation.bbs.service.BlogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    public static final String SQL_LIKE_MATCHING_TWO_WAY = "%%%s%%";

    @Autowired
    private BlogMapper mapper;

    private final String GMT_CREATE_TIME_DESC = " gmt_create desc ";
    private final String GMT_UPDATE_TIME_DESC = " gmt_update desc ";

    @Override
    public Pagination<Blog> selectAll(Integer currPage, Integer pageSize) {
        PageMethod.startPage(currPage, pageSize);
        BlogExample example = new BlogExample();
        example.setOrderByClause(GMT_CREATE_TIME_DESC);
        List<Blog> blogs = mapper.selectByExample(example);
        Pagination<Blog> blogPagination = new Pagination<>(blogs);
        return blogPagination;
    }

    @Override
    public Pagination<Blog> selectAll(String title, Integer currPage, Integer pageSize) {
        PageMethod.startPage(currPage, pageSize);
        BlogExample example = new BlogExample();
        example.setOrderByClause(GMT_CREATE_TIME_DESC);
        BlogExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(title)) {
            criteria.andTitleLike(String.format(SQL_LIKE_MATCHING_TWO_WAY, title));
        }
        List<Blog> blogs = mapper.selectByExample(example);
        Pagination<Blog> blogPagination = new Pagination<>(blogs);
        return blogPagination;
    }


    @Override
    public List<Blog> selectAllByUpdateDesc(Integer currPage, Integer pageSize) {
        PageMethod.startPage(currPage, pageSize);
        BlogExample example = new BlogExample();
        example.setOrderByClause(GMT_UPDATE_TIME_DESC);
        return mapper.selectByExampleWithBLOBs(example);
    }


    @Override
    public int insert(Blog record) {
        return mapper.insert(record);
    }

    @Override
    public Blog selectByBid(String bid) {
        BlogExample example = new BlogExample();
        BlogExample.Criteria criteria = example.createCriteria();
        criteria.andBidEqualTo(bid);
        List<Blog> blogs = mapper.selectByExampleWithBLOBs(example);
        if (blogs == null || blogs.isEmpty()) {
            return null;
        }
        return blogs.get(0);
    }

    @Override
    public int update(Blog record) {
        return mapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int delete(Integer id) {
        return mapper.deleteByPrimaryKey(id);
    }
}
