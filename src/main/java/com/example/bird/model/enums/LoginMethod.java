package com.example.bird.model.enums;

import org.apache.commons.lang3.StringUtils;

public enum LoginMethod implements IEnum {
    NORMAL_LOGIN("0", "普通登录"),
    THIRD_LOGIN("0", "第三方登录"),
    ;

    private String code;
    private String message;

    LoginMethod(String code, String message) {
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
    public IEnum[] allValue() {
        return values();
    }

    public static LoginMethod valueOfCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        LoginMethod[] values = values();
        for (LoginMethod e : values) {
            if (StringUtils.equals(e.getCode(), code)) {
                return e;
            }
        }
        return null;
    }
}
