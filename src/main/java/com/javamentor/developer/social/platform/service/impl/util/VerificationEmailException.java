package com.javamentor.developer.social.platform.service.impl.util;

public class VerificationEmailException extends RuntimeException {

    VerificationEmailException(String msg, Throwable cause) {
        super(msg, cause);
    }

    VerificationEmailException(String msg) {
        super(msg);
    }
}
