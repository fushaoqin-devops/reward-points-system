package com.example.rewardpointssystem.common.result;

public enum AppHttpCodeEnum {

    SUCCESS(200,"Request completed"),
    INVALID_PARAMS(400, "Invalid parameters"),
    SERVER_ERROR(500, "Server is down");

    int code;
    String errorMessage;

    AppHttpCodeEnum(int code, String errorMessage){
        this.code = code;
        this.errorMessage = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
