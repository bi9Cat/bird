package com.example.bird.controller;

import com.example.bird.controller.response.Result;
import com.example.bird.service.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(exception = BusinessException.class)
    public Result handleBusinessException(BusinessException exception) {
        return Result.error(exception);
    }

    @ExceptionHandler(exception = Throwable.class)
    public Result handleException(Exception e) {
        log.error("system exception.", e);
        return Result.error();
    }
}
