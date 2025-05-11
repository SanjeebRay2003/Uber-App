package Springboot.Uber.App.Services.Implementation;

import Springboot.Uber.App.Services.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class Send_Email implements EmailService {

    private final JavaMailSender javaMailSender;

    @Override
    public void sendEmail(String toEmail, String subject, String body) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

            simpleMailMessage.setTo(toEmail);
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(body);

            javaMailSender.send(simpleMailMessage);
            log.info("Email sent Successfully");
        }catch (Exception e){
            log.info("email cannot send "+e.getMessage());
        }
    }

    @Override
    public void sendEmailToMultiple(String[] toEmail, String subject, String body) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

            simpleMailMessage.setTo(toEmail);
//            simpleMailMessage.setBcc(toEmail);
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(body);

            javaMailSender.send(simpleMailMessage);
            log.info("Email sent Successfully");
        }catch (Exception e){
            log.info("email cannot send "+e.getMessage());
        }
    }
}
