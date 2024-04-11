package com.example.rewardpointssystem.common.exception;

import com.example.rewardpointssystem.common.result.AppHttpCodeEnum;

public class CustomException extends RuntimeException {

    private AppHttpCodeEnum appHttpCodeEnum;

    public CustomException(AppHttpCodeEnum appHttpCodeEnum){
        this.appHttpCodeEnum = appHttpCodeEnum;
    }

    public CustomException(AppHttpCodeEnum appHttpCodeEnum, String message){
        super(message);
        this.appHttpCodeEnum = appHttpCodeEnum;
    }

    public AppHttpCodeEnum getAppHttpCodeEnum() {
        return appHttpCodeEnum;
    }

}
