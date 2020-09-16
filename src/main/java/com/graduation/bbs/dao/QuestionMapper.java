package com.graduation.bbs.dao;

import com.graduation.bbs.model.Question;
import com.graduation.bbs.model.QuestionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface QuestionMapper {

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    long countByExample(QuestionExample example);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int deleteByExample(QuestionExample example);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int insert(Question record);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int insertSelective(Question record);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    List<Question> selectByExampleWithBLOBs(QuestionExample example);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    List<Question> selectByExample(QuestionExample example);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    Question selectByPrimaryKey(Integer id);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByExampleSelective(@Param("record") Question record, @Param("example") QuestionExample example);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByExampleWithBLOBs(@Param("record") Question record, @Param("example") QuestionExample example);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByExample(@Param("record") Question record, @Param("example") QuestionExample example);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByPrimaryKeySelective(Question record);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByPrimaryKeyWithBLOBs(Question record);

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    int updateByPrimaryKey(Question record);
}