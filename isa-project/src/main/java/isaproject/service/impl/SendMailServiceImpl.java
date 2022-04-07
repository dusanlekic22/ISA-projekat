package isaproject.service.impl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import isaproject.model.Mail;
import isaproject.service.SendMailService;

@Service
public class SendMailServiceImpl implements SendMailService {
	
    private final JavaMailSender javaMailSender;

    @Autowired
    public SendMailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendMail(Mail mail) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(mail.getRecipient(), mail.getRecipient());

        msg.setSubject(mail.getSubject());
        msg.setText(mail.getMessage());
        
        javaMailSender.send(msg);
    }

    @Override
    public void sendMailHTML(Mail mail) {
        MimeMessage msg = javaMailSender.createMimeMessage();
        try {
        	MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        	helper.setTo(mail.getRecipient());
			helper.setSubject(mail.getSubject());
			msg.setContent(mail.getMessage(),"text/html");
			javaMailSender.send(msg);
		} catch (MessagingException e) {
			e.printStackTrace();
		}

//        helper.addAttachment("hero.jpg", new ClassPathResource("hero.jpg"));

    }
}