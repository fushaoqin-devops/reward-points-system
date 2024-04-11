package com.example.rewardpointssystem.common.result;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {

    private String host;

    private Integer code;

    private String errorMessage;

    private T data;

    public Result() {
        this.code = 200;
    }

    public Result(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.errorMessage = msg;
        this.data = data;
    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.errorMessage = msg;
    }

    public static Result okResult(int code, String msg) {
        Result result = new Result();
        return result.ok(code, null, msg);
    }

    public static Result okResult(Object data) {
        Result result = setAppHttpCodeEnum(AppHttpCodeEnum.SUCCESS, AppHttpCodeEnum.SUCCESS.getErrorMessage());
        if(data!=null) {
            result.setData(data);
        }
        return result;
    }

    public static Result errorResult(int code, String msg) {
        Result result = new Result();
        return result.error(code, msg);
    }

    public Result<?> error(Integer code, String msg) {
        this.code = code;
        this.errorMessage = msg;
        return this;
    }

    public Result<?> ok(Integer code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.errorMessage = msg;
        return this;
    }

    // for the purpose of this project, we are returning 200 for all exceptions, actual status code is encapsulated in response
    public static Result errorResult(AppHttpCodeEnum enums){
        return setAppHttpCodeEnum(enums, enums.getErrorMessage());
    }

    private static Result setAppHttpCodeEnumError(AppHttpCodeEnum enums, String errorMessage) {
        return errorResult(enums.getCode(), errorMessage);
    }

    public static Result errorResult(AppHttpCodeEnum enums, String errorMessage){
        return setAppHttpCodeEnum(enums, errorMessage);
    }

    private static Result setAppHttpCodeEnum(AppHttpCodeEnum enums, String errorMessage){
        return okResult(enums.getCode(), errorMessage);
    }


}
