package com.graduation.bbs.controller;

import com.graduation.bbs.model.AdminUser;
import com.graduation.bbs.service.AdminUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description
 * @Author
 * @Version 1.0
 **/
@Controller
@RequestMapping(value = "/bbs/test")
public class TestController {

    @Resource
    private AdminUserService adminUserService;

    @RequestMapping(value = "/testReq", method = RequestMethod.GET)
    @ResponseBody
    public String test(@RequestParam Integer testId){
        return "test" + testId;
    }


    @GetMapping(value = "/testReq2")
    @ResponseBody
    public String test2(@RequestParam Integer testId){
        return "test2" + testId;
    }


    @RequestMapping(value = "/getAdminUser", method = RequestMethod.GET)
    @ResponseBody
    public AdminUser getAdminUser(@RequestParam Integer id){
        return adminUserService.selectByPrimaryKey(id);
    }


    @RequestMapping(value = "/getAdminUser2", method = RequestMethod.GET)
    public String getAdminUser2(@RequestParam Integer id, Model model){
        model.addAttribute("name","hello world");
        model.addAttribute("admin", adminUserService.selectByPrimaryKey(id));
        return "test";
    }



}
