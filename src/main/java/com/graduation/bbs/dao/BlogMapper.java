package com.graduation.bbs.dao;

import com.graduation.bbs.model.Blog;
import com.graduation.bbs.model.BlogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BlogMapper {
    /**
     * @mbg.generated generated automatically, do not modify!
     */
    long countByExample(BlogExample example);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int deleteByExample(BlogExample example);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int insert(Blog record);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int insertSelective(Blog record);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    List<Blog> selectByExampleWithBLOBs(BlogExample example);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    List<Blog> selectByExample(BlogExample example);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    Blog selectByPrimaryKey(Integer id);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByExampleSelective(@Param("record") Blog record, @Param("example") BlogExample example);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByExampleWithBLOBs(@Param("record") Blog record, @Param("example") BlogExample example);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByExample(@Param("record") Blog record, @Param("example") BlogExample example);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByPrimaryKeySelective(Blog record);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByPrimaryKeyWithBLOBs(Blog record);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByPrimaryKey(Blog record);
}