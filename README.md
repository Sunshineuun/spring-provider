> Spring 的实现

1. 全局统一异常实现.参考：`ExceptionHandle`
2. SpringBoot Actuator(监视器)【 [1](https://docs.spring.io/spring-boot/docs/2.6.3/reference/htmlsingle/) 】第13节 Production-ready Features
```yml
management:
  endpoints:
    enabled-by-default: true
    web:
      exposure:
        include: "*"
      cors:
        allowed-origins: "baidu.com" # 允许跨域访问的域名
        allowed-methods: "GET,POST" # 允许的请求方法
      base-path: /actuator
```

3. Nacos集成

> com.alibaba.cloud 使用2021.0.1.0 的版本。[1](https://spring.io/projects/spring-cloud-alibaba#learn)
- 服务注册
- 配置中心

4. 遇到的问题
> 2022年4月24日，干了一件非常愚蠢的事情。pom文件中定义当前项目的打包方式定义为了`<packaging>pom</packaging>`，导致项目启动一直异常，Maven打包的时候也异常。这里后续遇到后可以通过查看打包日志查看打的是`pom`还是`jar`。
```text
这个打的是jar，看第五行。
[INFO] Scanning for projects...
[INFO] 
[INFO] ---------------------< com.qiusm:spring-provider >----------------------
[INFO] Building spring-provider 0.0.1
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- maven-resources-plugin:3.2.0:resources (default-resources) @ spring-provider ---
这个打的是pom，看第五行。
[INFO] Scanning for projects...
[INFO] 
[INFO] ---------------------< com.qiusm:spring-provider >----------------------
[INFO] Building spring-provider 0.0.1
[INFO] --------------------------------[ pom ]---------------------------------
[INFO] 
[INFO] --- maven-resources-plugin:3.2.0:resources (default-resources) @ spring-provider ---
```

