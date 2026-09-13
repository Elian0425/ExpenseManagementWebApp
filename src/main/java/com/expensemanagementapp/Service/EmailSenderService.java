package com.expensemanagementapp.Service;

public interface EmailSenderService {

    void sendEmail(String emailTo, String emailSubject, String emailBody);
}
