package com.javamentor.developer.social.platform.service.abstracts.util;

import com.javamentor.developer.social.platform.models.entity.user.User;

public interface VerificationEmailService {

    void sendEmail(User user);
}
