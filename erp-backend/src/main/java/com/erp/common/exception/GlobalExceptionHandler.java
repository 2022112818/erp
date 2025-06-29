
package com.erp.common.exception;

import com.erp.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<String> handleValidationException(MethodArgumentNotValidException ex) {
        log.error(ex.toString());
        String errorMessage = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return Result.failure(errorMessage);
    }

    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception ex) {
        log.error(ex.toString());
        return Result.failure("系统异常：" + ex.getMessage());
    }
}
