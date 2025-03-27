package com.example.bird.model.enums;

import org.apache.commons.lang3.StringUtils;

public interface IEnum {
    String getCode();

    String getMessage();

    IEnum[] allValue();

    default int getIntCode() {
        return Integer.parseInt(getCode());
    }
}
