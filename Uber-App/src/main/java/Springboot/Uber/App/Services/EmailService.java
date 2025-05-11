package Springboot.Uber.App.Services;

public interface EmailService {

     void sendEmail(String toEmail,String subject,String body);
     void sendEmailToMultiple(String[] toEmail,String subject,String body);
}
