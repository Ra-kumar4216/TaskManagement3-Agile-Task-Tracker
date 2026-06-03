package com.TaskManagement3.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendResetPasswordMail(String to, String token) {
    	
    	String resetPasswordLink="http:/localhost:3306/auth/reset-password?token="+token;
        SimpleMailMessage message = new SimpleMailMessage();
        
        message.setTo(to);
        message.setSubject("Reset your password");
        message.setText("Click the link to reset your: " + resetPasswordLink);
        javaMailSender.send(message);
        System.out.println("Reset Email sent to: " + to);
    }
}