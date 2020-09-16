package com.graduation.bbs.model;

import java.util.Date;

/**
 * Table: comment
 */
public class Comment {
    /**
     * Column: id
     * Remark: 自增id
     */
    private Integer id;

    /**
     * Column: comment_id
     * Remark: 评论唯一id
     */
    private String commentId;

    /**
     * Column: topic_category
     * Remark: 1博客 2问答
     */
    private Integer topicCategory;

    /**
     * Column: topic_id
     * Remark: 评论主题id
     */
    private String topicId;

    /**
     * Column: user_id
     * Remark: 评论者id
     */
    private Integer userId;

    /**
     * Column: user_name
     * Remark: 评论者昵称
     */
    private String userName;

    /**
     * Column: gmt_create
     * Remark: 评论创建时间
     */
    private Date gmtCreate;

    /**
     * Column: content
     * Remark: 评论内容
     */
    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId == null ? null : commentId.trim();
    }

    public Integer getTopicCategory() {
        return topicCategory;
    }

    public void setTopicCategory(Integer topicCategory) {
        this.topicCategory = topicCategory;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId == null ? null : topicId.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}