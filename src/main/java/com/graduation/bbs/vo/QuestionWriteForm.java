package com.graduation.bbs.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
public class QuestionWriteForm {

    @ApiModelProperty(value = "问题标题")
    private String title;
    @ApiModelProperty(value = "问题内容")
    private String content;


    @ApiModelProperty(value = "作者id")
    private Integer authorId;
    @ApiModelProperty(value = "作者名称")
    private String authorName;

}
