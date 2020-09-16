package com.graduation.bbs.controller;

import com.graduation.bbs.constants.CommonConstant;
import com.graduation.bbs.dao.AdminUserMapper;
import com.graduation.bbs.model.AdminUser;
import com.graduation.bbs.service.AdminUserService;
import com.graduation.bbs.vo.RegisterForm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.util.Date;

@Controller
public class LoginController {

    @Resource
    private AdminUserService adminUserService;

    @Autowired
    private AdminUserMapper adminUserMapper;

//    @Autowired
//    InviteService inviteService;
//    @Autowired
//    UserService userService;
//    @Autowired
//    UserInfoService userInfoService;

    @GetMapping({"/","/index"})
    public String index(){
        return "index";
    }

    @GetMapping("/login")
    public String toLogin(HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        Object object = session.getAttribute("USER_INFO");
        if (object == null) {
            return "login";
        } else {
            AdminUser adminUser = (AdminUser) object;
            if (adminUser == null) {
                return "login";
            }
            model.addAttribute("loginMsg", adminUser.getUsername());
            return "index";
        }

    }

    @GetMapping("/register")
    public String toRegister(){
        return "register";
    }

    @RequestMapping(value = "/toLogout", method = RequestMethod.GET)
    public String toLogout(){
        return "logout";
    }

    @RequestMapping(value = "/toActivity", method = RequestMethod.GET)
    public String toActivity(){
        return "perfected/activity";
    }

    @RequestMapping(value = "/toRecruitment", method = RequestMethod.GET)
    public String toRecruitment(){
        return "perfected/recruitment";
    }

    @RequestMapping(value = "/logout/{id}", method = RequestMethod.GET)
    public String logout(@PathParam(value = "id") Integer id, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        Object object = session.getAttribute("USER_INFO");
        AdminUser adminUser = (AdminUser) object;
        session.removeAttribute("USER_INFO");
        model.addAttribute("registerMsg", adminUser.getUsername());
        return "logout";
//        return "redirect:/toLogout";
    }

    @GetMapping("/testTT2")
    public String testTT2(){
        return "index.html";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginToSystem(Model model, AdminUser user, HttpServletRequest request) {
        AdminUser temp = adminUserService.selectByUserName(user.getUsername());
        if (temp == null ) {
            temp = adminUserService.selectByEmail(user.getUsername());
            if (temp == null){
                model.addAttribute("registerMsg","用户名不存在");
                return "login";
            }
            user.setUsername(temp.getUsername());
        }
        temp = adminUserService.selectByUserNameAndPwd(user.getUsername(), user.getPassword());
        if (temp == null ){
            model.addAttribute("registerMsg","密码不正确，请重新输入");
            return "login";
        }


        AdminUser updateUser = new AdminUser();
        updateUser.setId(temp.getId());
        updateUser.setInTime(new Date());
        int count = adminUserMapper.updateByPrimaryKeySelective(updateUser);

        HttpSession session = request.getSession(true);
        session.setAttribute("USER_INFO",temp);

        model.addAttribute("registerMsg","登录成功");
        return "redirect:/index";

    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(Model model, RegisterForm registerForm){
        if (StringUtils.isBlank(registerForm.getPassword())) {
            model.addAttribute("registerMsg","密码不能为空");
            return "register";
        }

        if (!registerForm.getPassword().equals(registerForm.getRepassword())) {
            model.addAttribute("registerMsg","两次密码不相同");
            return "register";
        }

        AdminUser temp = null;
        temp = adminUserService.selectByUserName(registerForm.getUsername());
        if (temp != null){
            model.addAttribute("registerMsg","用户名重复");
            return "register";
        }

        temp = adminUserService.selectByEmail(registerForm.getEmail());
        if (temp != null){
            model.addAttribute("registerMsg","邮箱已被注册");
            return "register";
        }

        AdminUser adminUser = new AdminUser();
        adminUser.setUsername(registerForm.getUsername());
        adminUser.setPassword(registerForm.getPassword());
        adminUser.setRoleLevel(CommonConstant.ROLE_LEVEL_USER);
        adminUser.setTelephone(registerForm.getTelephone());
        adminUser.setEmail(registerForm.getEmail());

        int count = adminUserService.insert(adminUser);
        if (count <= 0){
            model.addAttribute("registerMsg","注册失败联系，请联系管理员");
            return "register";
        }

        model.addAttribute("registerMsg","注册成功, 请登录");
        //重定向到 登录页
        return "login";
    }


}
