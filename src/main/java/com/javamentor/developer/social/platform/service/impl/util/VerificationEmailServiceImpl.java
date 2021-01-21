package com.javamentor.developer.social.platform.service.impl.util;

import com.javamentor.developer.social.platform.service.abstracts.util.VerificationEmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class VerificationEmailServiceImpl implements VerificationEmailService {

    @Value("${mail.verification.from}")
    private String from;

    @Value("${mail.verification.subject}")
    private String subject;

    @Value("${mail.verification.url}")
    private String link;

    private final JavaMailSender emailSender;

    public VerificationEmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public void sendEmail(String email, String token) {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setFrom(from);
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText("<a href=\"" + link + token + "\">Подтвердить адрес эл. почты</a>", true);
            emailSender.send(message);
        } catch (MessagingException | MailException ex) {
            throw new VerificationEmailException(
                    String.format("Verification email message delivery failed, cause [%s]", ex.getMessage()), ex);
        }
    }
}
