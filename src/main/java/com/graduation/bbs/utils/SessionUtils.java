package com.graduation.bbs.utils;

import com.graduation.bbs.model.AdminUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtils {

    public static AdminUser getUserInSession(HttpServletRequest request){
        HttpSession session = request.getSession();
        Object object = session.getAttribute("USER_INFO");
        if (object == null){
            return null;
        }
        AdminUser user = (AdminUser) object;
        return user;
    }


}
