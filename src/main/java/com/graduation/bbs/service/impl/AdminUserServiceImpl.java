package com.graduation.bbs.service.impl;

import com.graduation.bbs.dao.AdminUserMapper;
import com.graduation.bbs.model.AdminUser;
import com.graduation.bbs.model.AdminUserExample;
import com.graduation.bbs.service.AdminUserService;
import com.graduation.bbs.vo.RegisterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    private AdminUserMapper adminUserMapper;

    @Override
    public AdminUser selectByPrimaryKey(Integer id) {
        return adminUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer insert(AdminUser adminUser) {
        return adminUserMapper.insert(adminUser);
    }

    @Override
    public Integer update(AdminUser adminUser) {
        return adminUserMapper.updateByPrimaryKeySelective(adminUser);
    }



    @Override
    public AdminUser selectByEmail(String email) {
        AdminUserExample example = new AdminUserExample();
        AdminUserExample.Criteria criteria = example.createCriteria();

        criteria.andEmailEqualTo(email);
        List<AdminUser> list = adminUserMapper.selectByExample(example);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }


    @Override
    public Boolean checkByEmailAndNotId(Integer id, String email) {
        AdminUserExample example = new AdminUserExample();
        AdminUserExample.Criteria criteria = example.createCriteria();

        criteria.andEmailEqualTo(email);
        criteria.andIdNotEqualTo(id);
        List<AdminUser> list = adminUserMapper.selectByExample(example);
        if (list == null || list.isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    public AdminUser selectByUserName(String userName) {
        AdminUserExample example = new AdminUserExample();
        AdminUserExample.Criteria criteria = example.createCriteria();

        criteria.andUsernameEqualTo(userName);
        List<AdminUser> list = adminUserMapper.selectByExample(example);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public AdminUser selectByUserNameAndPwd(String userName, String pwd) {
        AdminUserExample example = new AdminUserExample();
        AdminUserExample.Criteria criteria = example.createCriteria();

        criteria.andUsernameEqualTo(userName);
        criteria.andPasswordEqualTo(pwd);
        List<AdminUser> list = adminUserMapper.selectByExample(example);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

}
