package isaproject.service;

import isaproject.model.Mail;

public interface SendMailService {
    void sendMail(Mail mail);

    void sendMailHTML(Mail mail);
}
