package com.graduation.bbs.service;

import com.graduation.bbs.model.AdminUser;

public interface AdminUserService {

    AdminUser selectByPrimaryKey(Integer id);

    AdminUser selectByEmail(String email);

    /**
     * 校验 邮箱是否重复
     * @param id  not equals
     * @param email equals
     * @return true:邮箱不重复 ， false： 邮箱重复
     */
    Boolean checkByEmailAndNotId(Integer id, String email);


    AdminUser selectByUserName(String userName);

    AdminUser selectByUserNameAndPwd(String userName, String pwd);

    Integer insert(AdminUser adminUser);

    Integer update(AdminUser adminUser);
}
