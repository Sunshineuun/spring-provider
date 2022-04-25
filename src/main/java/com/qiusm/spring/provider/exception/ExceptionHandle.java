package com.qiusm.spring.provider.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局统一异常管理
 *
 * @author qiushengming
 */
@RestControllerAdvice
public class ExceptionHandle {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<String> handlerFlyException(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
