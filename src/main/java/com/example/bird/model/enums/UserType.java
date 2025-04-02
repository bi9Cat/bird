package com.example.bird.model.enums;

import org.apache.commons.lang3.StringUtils;

public enum UserType implements EnumInterface {
    ORDINARY_EMPLOYEES("0", "普通员工"),
    DEPARTMENT_MANAGER("1", "部门主管"),;
    private final String code;
    private final String message;

    UserType(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static UserType valueOfCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        UserType[] values = values();
        for (UserType e : values) {
            if (StringUtils.equals(e.getCode(), code)) {
                return e;
            }
        }
        return null;
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
