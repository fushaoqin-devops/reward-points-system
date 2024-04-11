package com.example.rewardpointssystem.common.exception;

import com.example.rewardpointssystem.common.result.AppHttpCodeEnum;
import com.example.rewardpointssystem.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class ExceptionCatch {

    /**
     * handle exception
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result exception(Exception e) {
        log.error("catch exception:{}", e.getMessage());

        return Result.errorResult(AppHttpCodeEnum.SERVER_ERROR);
    }

    /**
     * handle custom exception
     * @param e
     * @return
     */
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public Result exception(CustomException e) {
        log.error("catch exception:{}", e);
        return Result.errorResult(e.getAppHttpCodeEnum());
    }

}
