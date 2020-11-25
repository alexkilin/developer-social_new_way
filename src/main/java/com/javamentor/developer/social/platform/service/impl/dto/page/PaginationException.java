package com.javamentor.developer.social.platform.service.impl.dto.page;

public class PaginationException extends RuntimeException{
    PaginationException(String msg) {
        super(msg);
    }
}
