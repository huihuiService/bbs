package com.graduation.bbs.controller;


import com.graduation.bbs.cache.RamCache;
import com.graduation.bbs.constants.CommonConstant;
import com.graduation.bbs.model.AdminUser;
import com.graduation.bbs.service.AdminUserService;
import com.graduation.bbs.utils.FixUtils;
import com.graduation.bbs.vo.RegisterForm;
import javafx.scene.chart.ValueAxis;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;



/**
 *
 * @author
 * @since
 */
@Controller
public class UserInfoController {

    @Resource
    private AdminUserService adminUserService;

    // 更新用户资料
    @GetMapping("/userinfo/setting/{uid}")
    public String userSetting(@PathVariable Integer uid, Model model){
        // 用户信息回填
        userInfoCallBack(uid,model);

        return "user/settings";
    }

    // 更新用户资料
    @GetMapping("/userinfo/pwd/{uid}")
    public String userPwdInfo(@PathVariable Integer uid, Model model){
        // 用户信息回填
//        userInfoCallBack(uid,model);

        return "user/pwd";
    }

    @PostMapping("/userinfo/update/pwd/{uid}")
    public String userUpdatePwd(@PathVariable String uid, RegisterForm registerForm, HttpServletRequest request, Model model){

        if (StringUtils.isBlank(registerForm.getOldPassword())) {
            model.addAttribute("registerMsg","旧密码不能为空");
            return "user/pwd";
        }

        if (StringUtils.isBlank(registerForm.getPassword())) {
            model.addAttribute("registerMsg","新密码不能为空");
            return "user/find-pwd";
        }

        if (!registerForm.getPassword().equals(registerForm.getRepassword())) {
            model.addAttribute("registerMsg","两次密码不相同");
            return "user/pwd";
        }

        AdminUser adminUser = adminUserService.selectByPrimaryKey(registerForm.getId());
        if (!adminUser.getPassword().equals(registerForm.getOldPassword())){
            model.addAttribute("registerMsg","旧密码不对，请重新输入");
            return "user/pwd";
        }

        adminUser.setPassword(registerForm.getRepassword());

        // 更新用户信息;
        adminUserService.update(adminUser);

        return "redirect:/logout/" + registerForm.getId();

//        HttpSession session = request.getSession();
//        Object object = session.getAttribute("USER_INFO");
//        AdminUser temp = (AdminUser) object;
//        session.removeAttribute("USER_INFO");
//        model.addAttribute("registerMsg", "已修改密码，请重新登录");
//        return "redirect:/login";

    }


    @PostMapping("/userinfo/update/{uid}")
    public String userInfo(@PathVariable String uid, AdminUser adminUser, Model model){

        Boolean flag = adminUserService.checkByEmailAndNotId(adminUser.getId(), adminUser.getEmail());
        if (!flag) {
            model.addAttribute("registerMsg","邮箱已被注册");
            return "user/settings";
        }

        // 更新用户信息;
        adminUserService.update(adminUser);
        return "redirect:/index";
    }

    @GetMapping("/userinfo/findpwd")
    public String userFindpwd(Model model){
        return "user/find-pwd";
    }

    @PostMapping("/userinfo/findpwd")
    public String userFindpwd(RegisterForm registerForm, Model model) {
        if (StringUtils.isBlank(registerForm.getVefCode())) {
            model.addAttribute("registerMsg","验证码不能为空");
            return "user/find-pwd";
        }

        if (StringUtils.isBlank(registerForm.getPassword())) {
            model.addAttribute("registerMsg","密码不能为空");
            return "user/find-pwd";
        }

        if (!registerForm.getPassword().equals(registerForm.getRepassword())) {
            model.addAttribute("registerMsg","两次密码不相同");
            return "user/find-pwd";
        }

        AdminUser adminUser = null;
        adminUser = adminUserService.selectByEmail(registerForm.getEmail());
        if (adminUser == null){
            model.addAttribute("registerMsg","根据邮箱未获取到用户");
            return "user/find-pwd";
        }

        // 验证邮箱的 令牌是否正确
        Object object = RamCache.USER_VEF_CODE_MAP.get(adminUser.getId());
        if (object == null) {
            model.addAttribute("registerMsg","根据对此用户发送验证码，请校验");
            return "user/find-pwd";
        }
        String vefCode = (String) object;
        if (!registerForm.getVefCode().equals(vefCode)){
            model.addAttribute("registerMsg","验证码不对，请校验");
            return "user/find-pwd";
        }


        adminUser.setPassword(registerForm.getPassword());

        int count = adminUserService.update(adminUser);
        if (count <= 0){
            model.addAttribute("registerMsg","找回失败，请联系管理员");
            return "user/find-pwd";
        }

        model.addAttribute("registerMsg","找回成功, 请登录");
        //重定向到 登录页
        return "login";
    }

    @PostMapping("/userinfo/send/vefcode")
    @ResponseBody
    public Map<String, Object> sendVefCode(@RequestParam(value = "email", required = false) String email ) {
        Map<String, Object> responseMap = new HashMap<>();
        AdminUser adminUser = adminUserService.selectByEmail(email);
        if (adminUser == null){
            responseMap.put("code", 500);
            responseMap.put("msg", "根据邮箱未获取到用户");
            responseMap.put("data", null);
            return responseMap;
        }

        String vefCode = FixUtils.getVefCode();

        Boolean flag = FixUtils.sendVefCodeEmail(email, vefCode);
        if (!flag) {
            responseMap.put("code", 500);
            responseMap.put("msg", "发送验证码失败，请校验邮箱账号");
            responseMap.put("data", null);
            return responseMap;
        }

        RamCache.USER_VEF_CODE_MAP.put(adminUser.getId(), vefCode);

        responseMap.put("code", 200);
        responseMap.put("msg", "");
        responseMap.put("data", null);

        return responseMap;
    }

    // 用户信息回填
    private void userInfoCallBack(Integer uid, Model model){
        AdminUser adminUser = adminUserService.selectByPrimaryKey(uid);
        model.addAttribute("userInfo",adminUser);
    }


}

