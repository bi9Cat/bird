package com.example.bird.service.exception;

import com.example.bird.model.enums.EnumInterface;

public enum ErrorEnum implements EnumInterface {
    SUCCESS("E00000000", "成功"),
    SYSTEM_ERROR("E00000001", "服务器开小差了~"),
    USER_INFO_INPUT_EMPTY("E00000100", "用户信息不能为空"),
    USER_LOGIN_METHOD_IS_EMPTY("E00000101", "用户登录方式不能为空"),
    USER_PARAM_ERROR("E00000102", "请求参数有误"),
    USER_ID_ERROR("E00000103", "请输入正确的用户ID"),
    USER_NOT_EXIST("E00000104", "用户信息不存在"),
    USER_PASSWORD_EMPTY("E00000105", "用户密码不能为空"),
    USER_TYPE_IS_EMPTY("E00000106", "用户类型不能为空"),
    USER_DIRECT_SUPERVISOR_EMPTY("E00000107", "直属主管不能为空"),
    USER_PHONE_NUMBER_ERROR("E00000108", "手机号格式不正确"),
    USER_EMAIL_ERROR("E00000109", "邮箱格式不正确"),;

    private String code;
    private String message;

    ErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public EnumInterface[] allValue() {
        return values();
    }
}
