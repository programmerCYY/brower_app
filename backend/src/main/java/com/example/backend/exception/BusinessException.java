package com.example.backend.exception;


import com.example.backend.VO.Result;
import com.example.backend.enums.ErrorCode;

/**
 * 自定义业务异常类，继承RuntimeException
 * 结合[全局异常处理]使用
 * */
public class BusinessException extends RuntimeException {
    private static long serialVersionUID = 1L;

    private ErrorCode errorCode;

    private String detailMessage;

    public BusinessException() {};

    public BusinessException(ErrorCode errorCode) {
        this(errorCode, "");
    }

    public BusinessException(ErrorCode errorCode, String detailMessage) {
        super(errorCode.getMsg() + " : " + detailMessage);
        this.errorCode = errorCode;
        this.detailMessage = detailMessage;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getDetailMessage() {
        return detailMessage;
    }

    public Result getErrorResult() {
        return Result.BAD().status(errorCode.getHttpStatus()).msg(errorCode.getMsg()).data(detailMessage).build();
    }

}
