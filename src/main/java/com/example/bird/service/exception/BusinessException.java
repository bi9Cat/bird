package com.example.bird.service.exception;

import lombok.Data;

@Data
public class BusinessException extends RuntimeException {
    private String code;
    private String message;

    public BusinessException(ErrorEnum errorEnum) {
        if (errorEnum == null) {
            throw new RuntimeException("errorEnum is null");
        }
        this.code = errorEnum.getCode();
        this.message = errorEnum.getMessage();
    }
}
