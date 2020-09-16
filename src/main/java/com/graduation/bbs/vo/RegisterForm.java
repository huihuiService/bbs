package com.graduation.bbs.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
public class RegisterForm {

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "确认密码")
    private String repassword;

    @ApiModelProperty(value = "手机号码")
    private String telephone;

    @ApiModelProperty(value = "邮箱地址")
    private String email;


    @ApiModelProperty(value = "验证码")
    private String vefCode;


    @ApiModelProperty(value = "旧密码")
    private String oldPassword;

    @ApiModelProperty(value = "主键ID")
    private Integer id;


}
