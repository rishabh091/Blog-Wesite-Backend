package com.blogging_website.blogging.config;

import org.springframework.context.annotation.Configuration;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;

@Configuration
public class ForgetPasswordConfig {

    public static void sendMail(String userEmail,String subject,String content) throws Exception{
        Properties properties=new Properties();
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");

        Session session=Session.getInstance(properties,new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication("rishabhmalhotra9211@gmail.com","SyncMasterB1930");
            }
        });

        Message message=new MimeMessage(session);
        message.setFrom(new InternetAddress("rishabhmalhotra9211@gmail.com",false));

        message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(userEmail));
        message.setSubject(subject);
        message.setContent("SkyLine Email","text/html");
        message.setSentDate(new Date());

        MimeBodyPart mimeBodyPart=new MimeBodyPart();
        mimeBodyPart.setContent(content,"text/html");

        Multipart multipart=new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);
        message.setContent(multipart);

        Transport.send(message);
    }
}
