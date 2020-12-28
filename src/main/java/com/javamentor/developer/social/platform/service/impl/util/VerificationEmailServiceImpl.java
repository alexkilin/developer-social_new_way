package com.javamentor.developer.social.platform.service.impl.util;

import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.service.abstracts.model.token.VerificationTokenService;
import com.javamentor.developer.social.platform.service.abstracts.util.VerificationEmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final  VerificationTokenService verificationTokenService;

    public VerificationEmailServiceImpl(JavaMailSender emailSender, VerificationTokenService verificationTokenService) {
        this.emailSender = emailSender;
        this.verificationTokenService = verificationTokenService;
    }

    @Override
    public void sendEmail(User user) {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setFrom(from);
            helper.setTo(user.getEmail());
            helper.setSubject(subject);
            helper.setText("<a href=\"" + link + getUserTokenValue(user) + "\">Подтвердить адрес эл. почты</a>", true);
            emailSender.send(message);
        } catch (MessagingException | MailException ex) {
            throw new VerificationEmailException(
                    String.format("Verification email message delivery failed, cause [%s]", ex.getMessage()), ex);
        }
    }

    @Transactional
    protected String getUserTokenValue(User user) {
        return verificationTokenService.getById(user.getUserId()).orElseThrow(() ->
                new VerificationEmailException(String.format(
                        "Verification email message creation failed, cause [no verification token found for user with email '%s']",
                        user.getEmail())))
                .getValue();
    }
}
