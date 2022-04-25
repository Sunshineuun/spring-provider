package com.qiusm.spring.provider.service;


import com.qiusm.spring.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author qiushengming
 */
@Slf4j
@DubboService(version = "1.0")
public class HelloServiceImpl implements HelloService, InitializingBean {
    @Override
    public String test(String name) {
        return "hello " + name;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("Hello Service 服务创建成功 ！！！");
    }
}
