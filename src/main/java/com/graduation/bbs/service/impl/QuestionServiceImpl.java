package com.graduation.bbs.service.impl;

import com.github.pagehelper.page.PageMethod;
import com.graduation.bbs.common.Pagination;
import com.graduation.bbs.dao.QuestionMapper;
import com.graduation.bbs.model.Question;
import com.graduation.bbs.model.QuestionExample;
import com.graduation.bbs.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @Author
 * @Date 2020-09-14 17:50
 * @Version 1.0
 **/
@Service
public class QuestionServiceImpl implements QuestionService {

    public static final String SQL_LIKE_MATCHING_TWO_WAY = "%%%s%%";

    @Autowired
    private QuestionMapper mapper;

    private final String GMT_CREATE_TIME_DESC = " gmt_create desc ";
    private final String GMT_UPDATE_TIME_DESC = " gmt_update desc ";


    @Override
    public Pagination<Question> selectAll(Integer currPage, Integer pageSize) {
        PageMethod.startPage(currPage, pageSize);
        QuestionExample example = new QuestionExample();
        example.setOrderByClause(GMT_CREATE_TIME_DESC);
        List<Question> questions = mapper.selectByExampleWithBLOBs(example);
        Pagination<Question> questionList = new Pagination<>(questions);
        return questionList;
    }



    @Override
    public Pagination<Question> selectAll(String title, Integer currPage, Integer pageSize) {
        PageMethod.startPage(currPage, pageSize);
        QuestionExample example = new QuestionExample();
        example.setOrderByClause(GMT_CREATE_TIME_DESC);
        QuestionExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(title)) {
            criteria.andTitleLike(String.format(SQL_LIKE_MATCHING_TWO_WAY, title));
        }

        List<Question> questions = mapper.selectByExampleWithBLOBs(example);
        Pagination<Question> questionList = new Pagination<>(questions);
        return questionList;
    }

    @Override
    public Question selectByQid(String qid) {
        QuestionExample example = new QuestionExample();
        QuestionExample.Criteria criteria = example.createCriteria();
        criteria.andQidEqualTo(qid);
        List<Question> questions = mapper.selectByExampleWithBLOBs(example);
        if (questions == null || questions.isEmpty()) {
            return null;
        }
        return questions.get(0);
    }

    @Override
    public List<Question> selectAllByUpdateDesc(Integer currPage, Integer pageSize) {
        PageMethod.startPage(currPage, pageSize);
        QuestionExample example = new QuestionExample();
        example.setOrderByClause(GMT_UPDATE_TIME_DESC);
        return mapper.selectByExample(example);
    }

    @Override
    public int insert(Question record) {
        return mapper.insert(record);
    }

    @Override
    public int delete(Integer id) {
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(Question record) {
        return mapper.updateByPrimaryKeySelective(record);
    }




}
