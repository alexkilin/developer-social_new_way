package com.javamentor.developer.social.platform.webapp.controllers.advice;

import com.javamentor.developer.social.platform.service.impl.dto.pagination.PaginationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class AdviceController  extends ResponseEntityExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> constraintEx(RuntimeException runtimeException, WebRequest webRequest) {
        return exceptionHandlerResponse(runtimeException, webRequest);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<Object> apiRequestException(RuntimeException runtimeException, WebRequest webRequest) {
        return exceptionHandlerResponse(runtimeException, webRequest);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> notFoundEx(RuntimeException runtimeException, WebRequest webRequest) {
        return exceptionHandlerResponse(runtimeException, webRequest);
    }

    @ExceptionHandler(PaginationException.class)
    protected ResponseEntity<Object> paginationException(RuntimeException runtimeException, WebRequest webRequest) {
        return exceptionHandlerResponse(runtimeException, webRequest);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers, HttpStatus status,
                                                                          WebRequest request) {
        String name = ex.getParameterName();
        logger.error(name + " parameter is missing");

        return ResponseEntity.badRequest().body(name + " parameter is missing");
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        String[] m = ex.getCause().getMessage().split(":");
        return ResponseEntity.badRequest().body(m[0]);
    }

    private ResponseEntity<Object> exceptionHandlerResponse(RuntimeException runtimeException, WebRequest webRequest) {
        logger.info(runtimeException.fillInStackTrace().toString());
        String[] body = runtimeException.getMessage().split(":");
        return handleExceptionInternal(runtimeException, body[body.length - 1].trim(), new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);
    }
}
