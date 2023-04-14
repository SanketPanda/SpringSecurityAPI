package com.sanket.security.service.email;

import com.sanket.security.common.constant.Constants;
import com.sanket.security.dao.ConfirmationTokenRepository;
import com.sanket.security.model.ConfirmationToken;
import com.sanket.security.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class EmailService {

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    ConfirmationTokenRepository repo;

    @Value("${ui.app.url}")
    private String uiAppUrl;

    @Async
    public void sendAccountVerificationEmail(final User user){
        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        repo.save(confirmationToken);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject(Constants.ACCOUNT_VERIFICATION_EMAIL_SUBJECT);
        mailMessage.setFrom(senderEmail);
        final String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        mailMessage.setText(Constants.ACCOUNT_VERIFICATION_EMAIL_BODY + baseUrl +
                "/api/auth/confirm-account?token=" + confirmationToken.getConfirmationToken());
        //if(!user.getEmail().equals("sanketpanda37@gmail.com")) return;
        javaMailSender.send(mailMessage);
    }

    @Async
    public void sendPasswordResetEmail(final User user){
        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        repo.save(confirmationToken);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject(Constants.PASSWORD_RESET_EMAIL_SUBJECT);
        mailMessage.setFrom(senderEmail);
        mailMessage.setText(Constants.PASSWORD_RESET_EMAIL_BODY + uiAppUrl +
                "/reset-password?token=" + confirmationToken.getConfirmationToken());
        //if(!user.getEmail().equals("sanketpanda37@gmail.com")) return;
        javaMailSender.send(mailMessage);
    }

    @Async
    public void sendForgetPasswordResetEmail(final User user){
        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        repo.save(confirmationToken);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject(Constants.FORGET_PASSWORD_EMAIL_SUBJECT);
        mailMessage.setFrom(senderEmail);
        mailMessage.setText(Constants.FORGET_PASSWORD_EMAIL_BODY + uiAppUrl +
                "/forget-password?token=" + confirmationToken.getConfirmationToken());
        //if(!user.getEmail().equals("sanketpanda37@gmail.com")) return;
        javaMailSender.send(mailMessage);
    }
}
