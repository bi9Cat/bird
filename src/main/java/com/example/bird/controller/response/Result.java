package com.example.bird.controller.response;

import com.example.bird.service.exception.BusinessException;
import com.example.bird.service.exception.ErrorEnum;
import lombok.Data;

@Data
public class Result<T> {
    private String code;
    private String message;
    private T data;

    public Result(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(ErrorEnum errorEnum, T data) {
        if (errorEnum == null) {
            throw new RuntimeException("errorEnum is null");
        }
        this.code = errorEnum.getCode();
        this.message = errorEnum.getMessage();
        this.data = data;
    }

    public static Result success() {
        return new Result(ErrorEnum.SUCCESS, null);
    }

    public static <U> Result success(U data) {
        return new Result(ErrorEnum.SUCCESS, data);
    }

    public static Result error(BusinessException exception) {
        if (exception == null) {
            throw new RuntimeException("exception is null");
        }
        return new Result(exception.getCode(), exception.getMessage(), null);
    }

    public static Result error() {
        return new Result(ErrorEnum.SYSTEM_ERROR, null);
    }

    public static Result error(ErrorEnum errorEnum) {
        return new Result(errorEnum, null);
    }
}
