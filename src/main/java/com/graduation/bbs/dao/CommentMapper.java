package com.graduation.bbs.dao;

import com.graduation.bbs.model.Comment;
import com.graduation.bbs.model.CommentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CommentMapper {

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    long countByExample(CommentExample example);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int deleteByExample(CommentExample example);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int insert(Comment record);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int insertSelective(Comment record);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    List<Comment> selectByExampleWithBLOBs(CommentExample example);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    List<Comment> selectByExample(CommentExample example);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    Comment selectByPrimaryKey(Integer id);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByExampleSelective(@Param("record") Comment record, @Param("example") CommentExample example);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByExampleWithBLOBs(@Param("record") Comment record, @Param("example") CommentExample example);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByExample(@Param("record") Comment record, @Param("example") CommentExample example);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByPrimaryKeySelective(Comment record);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByPrimaryKeyWithBLOBs(Comment record);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByPrimaryKey(Comment record);
}