package in.Sanket.authify.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService
{
    private final JavaMailSender mailSender;

    @Value("${spring.mail.properties.mail.from}")  //injecting the from email from the application.properties
    private String fromEmail;

    public void sendWelcomeEmail(String toEmail, String name)
    {
        SimpleMailMessage message =  new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject("Welcome to our authify platfrom");
        message.setText("Hello"+name+",\n\nThanks for registering with us!\n\nRegards,\nAuthify Team");

        mailSender.send(message); //sendin the email


    }

    public void sendResetOtpEmail(String toEmail, String otp)
    {
        SimpleMailMessage message =  new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject("password reset otp");
        message.setText("your otp for resetting your password is "+otp+". use this Otp to procees with resetting your password.");

        mailSender.send(message);

    }

    public void sendOtpEmail(String toEmail, String otp)
    {
        SimpleMailMessage message =  new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject("Account verfication otp");
        message.setText("Your otp"+otp+". verify your account using this OTP.");

        mailSender.send(message);
    }
}
