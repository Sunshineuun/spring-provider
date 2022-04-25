package com.qiusm.spring.provider.controller;

import com.qiusm.parent.base.model.BaseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author qiushengming
 */
@RequestMapping("/hello")
@RestControllerAdvice
public class HelloController {

    @GetMapping(value = "/{string}")
    public BaseDTO<String> echo(@PathVariable String string) {
        BaseDTO<String> baseDTO = new BaseDTO<>();
        baseDTO.setMsg("Hello Nacos Discovery " + string);
        return baseDTO;
    }
}
