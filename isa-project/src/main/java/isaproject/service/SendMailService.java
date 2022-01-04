package isaproject.service;

import javax.mail.MessagingException;

import isaproject.model.Mail;

public interface SendMailService {
    void sendMail(Mail mail);

    void sendMailHTML(Mail mail) throws MessagingException;
}
