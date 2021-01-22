package com.javamentor.developer.social.platform.service.abstracts.util;

public interface VerificationEmailService {

    void sendEmail(String email, String token);
}
