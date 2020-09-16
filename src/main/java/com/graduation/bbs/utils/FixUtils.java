package com.graduation.bbs.utils;

import org.apache.commons.mail.EmailException;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

public class FixUtils {

    public static String getUuid(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    public static Timestamp getTime(){
        return new Timestamp(System.currentTimeMillis());
    }


    public static String getVefCode(){
        String random=(int)((Math.random()*9+1)*100000)+"";
        return random;
    }

//    public static void main(String[] args) {
//        sendVefCodeEmail(SendMailUtils.toMail, getVefCode());
//    }


    public static Boolean sendVefCodeEmail(String email, String vefCode) {
        String template = "正在进行IT论坛找回密码,您的验证码是:%s , 请妥善保管。";
        String context = String.format(template, vefCode);
        // 发送邮件
        try {
            SendMailUtils.sendPlainEmail(email, vefCode);
        } catch (EmailException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return false;
        }
        return true;

    }
}
