package com.graduation.bbs.utils;

import com.sun.mail.util.MailSSLSocketFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Properties;

/**
 * @Description
 * @Author tusdao-xh
 * @Date 2020-09-16 14:39
 * @Version 1.0
 **/
public class SendMailUtils {

    //设置邮件服务器
    private static final String send_email_host_name = "smtp.qq.com";

    // 发件人邮箱地址
    private static String fromMail = "1126368679@qq.com";
    // 发件人邮箱客户端授权码
    private static String password = "jatdetzmxljjfebf";

    public static String toMail = "504836776@qq.com";


    public static void  sendPlainEmail(String receiver, String vefCode) throws EmailException {
        sendPlainEmail(receiver, "找回密码", vefCode);
    }

    /**
     * 发送纯文本的Email
     * @param receiver 收信人
     * @param subjecct Email主题
     * @param vefCode Email内容
     * @throws EmailException
     */
    public static void  sendPlainEmail(String receiver, String subjecct, String vefCode) throws EmailException {
        Properties props = new Properties();

        // 开启debug调试
//        props.setProperty("mail.debug", "true");
        // 发送服务器需要身份验证
        props.setProperty("mail.smtp.auth", "true");
        // 设置邮件服务器主机名
        props.setProperty("mail.host", send_email_host_name);
        // 发送邮件协议名称
        props.setProperty("mail.transport.protocol", "smtp");

        try {
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            props.put("mail.smtp.ssl.enable", "true");
            props.put("mail.smtp.ssl.socketFactory", sf);

            Session session = Session.getInstance(props);

            Message msg = new MimeMessage(session);
            msg.setSubject(subjecct);

            String template = "IT社区的找回密码验证码:%s,请不要告诉别人！";
            msg.setText(String.format(template, vefCode));
            msg.setFrom(new InternetAddress(fromMail));

            Transport transport = session.getTransport();
            transport.connect(send_email_host_name, fromMail, password);

            transport.sendMessage(msg, new Address[]{new InternetAddress(receiver)});

            transport.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws EmailException { // 做测试用


        sendPlainEmail(toMail, "找回密码", "abcdef");


    }


}
