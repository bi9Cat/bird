package com.example.bird.model.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.StringUtils;

@Schema(description = "部门状态")
public enum DepartmentStatus implements EnumInterface {
    @Schema(description = "正常")
    NORMAL("0", "正常"),
    @Schema(description = "已删除")
    DELETED("1", "已删除"),
    @Schema(description = "停用")
    STOP("2", "停用"),;

    private String code;
    private String message;

    DepartmentStatus(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static DepartmentStatus valueOfCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        DepartmentStatus[] values = values();
        for (DepartmentStatus e : values) {
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
