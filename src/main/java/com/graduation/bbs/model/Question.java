package com.graduation.bbs.model;

import java.util.Date;

/**
 * Table: question
 */
public class Question {
    /**
     * Column: id
     * Remark: 自增id
     */
    private Integer id;

    /**
     * Column: qid
     * Remark: 问题id
     */
    private String qid;

    /**
     * Column: title
     * Remark: 问题标题
     */
    private String title;

    /**
     * Column: status
     * Remark: 状态 0 未解决 1 已解决
     */
    private Integer status;

    /**
     * Column: sort
     * Remark: 排序 0 普通  1 置顶
     */
    private Integer sort;

    /**
     * Column: views
     * Remark: 浏览量
     */
    private Integer views;

    /**
     * Column: author_id
     * Remark: 作者id
     */
    private Integer authorId;

    /**
     * Column: author_name
     * Remark: 作者名
     */
    private String authorName;

    /**
     * Column: gmt_create
     * Remark: 创建时间
     */
    private Date gmtCreate;

    /**
     * Column: gmt_update
     * Remark: 修改时间
     */
    private Date gmtUpdate;

    /**
     * Column: content
     * Remark: 问题内容
     */
    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQid() {
        return qid;
    }

    public void setQid(String qid) {
        this.qid = qid == null ? null : qid.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName == null ? null : authorName.trim();
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtUpdate() {
        return gmtUpdate;
    }

    public void setGmtUpdate(Date gmtUpdate) {
        this.gmtUpdate = gmtUpdate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}