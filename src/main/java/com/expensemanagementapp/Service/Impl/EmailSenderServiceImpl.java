package com.expensemanagementapp.Service.Impl;

import com.expensemanagementapp.Service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderServiceImpl implements EmailSenderService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendEmail(String emailTo, String emailSubject, String emailBody) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(emailSubject);
        mailMessage.setText(emailBody);
        javaMailSender.send(mailMessage);
    }
}
