package isaproject.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import isaproject.config.PasswordEncoderService;
import isaproject.dto.UserDTO;
import isaproject.mapper.UserMapper;
import isaproject.model.Mail;
import isaproject.model.User;
import isaproject.repository.UserRepository;
import net.bytebuddy.utility.RandomString;

@Service
public class CustomerServiceImpl implements CustomerService {
 
    @Autowired
    private UserRepository repo;
     
    @Autowired
    private PasswordEncoderService passwordEncoder;
     
    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    private SendMailService service;
 
    
     
    public void register(UserDTO userDTO, String siteURL) throws UnsupportedEncodingException, MessagingException 
    	   {
    			UserMapper mapper = new UserMapper();
    			User user = mapper.UsertoUserDTO(userDTO); 
    		    String encodedPassword = passwordEncoder.passwordEncoder().encode(user.getPassword());

    		    user.setPassword(encodedPassword);
    		     
    		    String randomCode = RandomString.make(64);
    		    user.setVerificationCode(randomCode);
    		    user.setEnabled(false);
    		     
    		    repo.save(user);
    		         
    		    sendVerificationEmail(user, siteURL);
    }
     
    public void sendVerificationEmail(User user, String siteURL) throws MessagingException
    	 {
    		    String toAddress = user.getEmail();
    		    String fromAddress = "Your email address";
    		    String senderName = "Your company name";
    		    String subject = "Please verify your registration";
    		    String content = "Dear " + user.getFirstName() +",<br>"
    		            + "Please click the link below to verify your registration:<br>"
    		            + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
    		            + "Thank you,<br>"
    		            + "Your company name.";
    		    
    		    String verifyURL = siteURL + "/auth/verify?code=" + user.getVerificationCode();
    		     
                content = content.replace("[[URL]]", verifyURL);
    		   
    		    Mail mail = new Mail(toAddress,subject,content);
    		    
    		    
    		    service.sendMailHTML(mail);
    }
    
    
    public boolean verify(String verificationCode) {
        User user = repo.findByVerificationCode(verificationCode);
         
        if (user == null || user.isEnabled()) {
            return false;
        } else {
            user.setVerificationCode(null);
            user.setEnabled(true);
            repo.save(user);
             
            return true;
        }
         
    }
         
}
