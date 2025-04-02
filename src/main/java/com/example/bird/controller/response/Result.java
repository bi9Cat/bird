package com.example.bird.controller.response;

import com.example.bird.service.exception.BusinessException;
import com.example.bird.service.exception.ErrorEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "响应结果")
@Data
public class Result<T> {
    @Schema(description = "返回码", requiredMode = Schema.RequiredMode.REQUIRED, example = "E00000000")
    private String code;
    @Schema(description = "返回码描述", requiredMode = Schema.RequiredMode.REQUIRED, example = "请求成功")
    private String message;
    @Schema(description = "返回数据")
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
