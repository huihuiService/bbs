package com.graduation.bbs.utils;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Properties;

/**
 * @Description
 * @Author tusdao-xh
 * @Date 2020-09-16 17:51
 * @Version 1.0
 **/
public class SendmailUtils2 {

    //设置邮件服务器
    private static final String send_email_host_name = "smtp.qq.com";

    // 发件人邮箱地址
    private static String fromMail = "1126368679@qq.com";
    // 发件人邮箱客户端授权码
    private static String password = "jatdetzmxljjfebf";

    private static String toMail = "50483676@qq.com";

    public static void main(String[] args) {

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
            msg.setSubject("seenews 错误");
            StringBuilder builder = new StringBuilder();
            builder.append("url = " + "http://blog.csdn.net/never_cxb/article/details/50524571");
            builder.append("\n页面爬虫错误");
            builder.append("\n时间 " + new Timestamp(System.currentTimeMillis()));
            msg.setText(builder.toString());
            msg.setFrom(new InternetAddress("1126368679@qq.com"));

            Transport transport = session.getTransport();
            transport.connect(send_email_host_name, "1126368679@qq.com", "jatdetzmxljjfebf");

            transport.sendMessage(msg, new Address[]{new InternetAddress("504836776@qq.com")});

            transport.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }

    }
}
