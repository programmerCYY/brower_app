package com.example.backend.exception;


import com.example.backend.VO.Result;
import com.example.backend.enums.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;
import java.util.function.Consumer;

/**
 * 控制器增强
 * 用来捕获@RequestMapping的方法中所有抛出的SparrowException
 * 将error message 放入Result中，返回给前端
 * @see Result
 * @see BusinessException
 * */
@ControllerAdvice
public class GlobalDefaultExceptionHandler {
    private static Logger logger = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);

    /**
     * 处理逻辑异常或其他越界异常
     * @param ex
     * @param response
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public Result errorHandle(BusinessException ex, HttpServletResponse response) {
        logger.error(ex.getMessage());
        logger.error(ex.getDetailMessage());

//        HttpUtil.setResponseStatus(response, ex.getErrorCode());
        return ex.getErrorResult();
    }

    /**
     * 处理参数校验失败异常
     * @param ex
     * @return
     */
    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseBody
    public Result resolveConstraintViolationException(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        if (!CollectionUtils.isEmpty(constraintViolations)) {
            StringBuilder msgBuilder = new StringBuilder();
            for (ConstraintViolation constraintViolation :constraintViolations) {
                msgBuilder.append(constraintViolation.getMessage()).append(",");
            }
            String errorMessage = msgBuilder.toString();
            if (errorMessage.length() > 1) {
                errorMessage = errorMessage.substring(0, errorMessage.length() - 1);
            }
            return Result.BAD().status(ErrorCode.PARAM_VALIDATION_ERROR.getHttpStatus()).msg(ErrorCode.PARAM_VALIDATION_ERROR.getMsg()).data(errorMessage).build();
        }
        return Result.BAD().status(ErrorCode.PARAM_VALIDATION_ERROR.getHttpStatus()).msg(ErrorCode.PARAM_VALIDATION_ERROR.getMsg()).data(ex.getMessage()).build();
    }


    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    public Result resolveMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        StringBuilder msgBuilder = new StringBuilder();

        ex.getAllErrors().forEach(new Consumer<ObjectError>() {
            @Override
            public void accept(ObjectError objectError) {
                msgBuilder.append(objectError.getDefaultMessage()+"   ");
            }
        });
        return Result.BAD().status(ErrorCode.PARAM_VALIDATION_ERROR.getHttpStatus()).msg(ErrorCode.PARAM_VALIDATION_ERROR.getMsg()).data(msgBuilder).build();
    }
}
