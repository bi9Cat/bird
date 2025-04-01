package com.example.bird.model.enums;

public interface EnumInterface {
    String getCode();

    String getMessage();

    EnumInterface[] allValue();

    default int getIntCode() {
        return Integer.parseInt(getCode());
    }
}
