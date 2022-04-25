# POM的依赖

统一用以下的依赖。版本见parent的依赖

```xml
<!-- 如果resource下定义了 bootstrap.xml 并且你希望它被作为配置文件读取，那么就加上这个依赖 -->
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-bootstrap</artifactId>
</dependency>
<!-- dubbo的依赖 -->
<dependency>
  <groupId>com.alibaba.cloud</groupId>
  <artifactId>spring-cloud-starter-dubbo</artifactId>
</dependency>

<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-webflux</artifactId>
</dependency>

<!-- nacos -->
<dependency>
  <groupId>com.alibaba.cloud</groupId>
  <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
</dependency>
<dependency>
  <groupId>com.alibaba.cloud</groupId>
  <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
</dependency>
```

# Logging的配置

在使用BootStrap.yaml配置文件之后，将下述配置配置在application.yml中将会引起错误。原因不详。

错误：找不到logback-config.xml

```yml
logging:
  level:
    root: info
  config: classpath:logback-config.xml
  file:
    path: /tmp/logs
```

# Nacos配置

1. 在无法读取配置中心时，请第一时间关注是否bootstrap.yaml配置文件被读取了。Nacos的配置都在这个里面。
2. 一定要检查Nacos配置对应的地址、用户名、密码等信息。

```yml
spring:
  application:
    name: spring-provider
  # 解决Dubbo的循环依赖. 2.7版本之后必须配置，否则启动项目时将提示循环依赖无法解决
  main:
    allow-circular-references: true
  cloud:
    nacos:
      username: nacos
      password: nacos
      server-addr: 192.168.1.8:8848
      config:
        file-extension: yaml
        # shared-configs: log.yml,spring.yml
        # refresh-enabled: true
        # 在Nacos配置中心配置文件时，一定要对dataId带有后缀名。否则无法解析。见下图
        extension-configs:
          - data-id: ${spring.application.name}.yaml
            group: DEFAULT_GROUP
            refresh: true
          - data-id: log.yaml
            group: DEFAULT_GROUP
            refresh: true
        enabled: false
      discovery:
        # 控制是否注册到Nacos上
        enabled: true
```

![image-20220425111405320](/Users/qiushengming/Library/Application Support/typora-user-images/image-20220425111405320.png)

# Dubbo配置

## Provide （生产者）

### yml 配置

1. 当服务没有注册到Nacos时关注以下：
   - Dubbo.scan.base-packages 是否配置正确，让dubbo扫描到了服务类。
   - dubbo.registry.address 配置的是否向nacos注册。正确格式：nacos://ip:prot 。也有用spring-cloud作为协议的。但是那是spring cloud的。配置中心用啥，就写啥。

```yaml
dubbo:
  scan:
    # 配置包名，dubbo 回去扫面当前包下呗 @DubboServie 注释的类，并将该类注册为服务。
    # @DubboService 在2.7版本后正式启用之前的 @Service 被启用。
    base-packages: com.qiusm.spring.provider.service
  # 协议相关的定义
  protocol:
    name: dubbo
    # dubbo协议缺省端口为20880，rmi协议缺省端口为1099，http和hessian协议缺省端口为80；
    # 如果没有配置port，则自动采用默认端口，如果配置为-1，则会分配一个没有被占用的端口。
    # Dubbo 2.4.0+，分配的端口在协议缺省端口的基础上增长，确保端口段可控
    port: -1
  registry:
    # dubbo服务注册端口，注册中心服务器地址，如果地址没有端口缺省为9090，同一集群内的多个地址用逗号分隔，如：ip:port,ip:port
    # 其中前缀spring-cloud说明：挂载到 Spring Cloud注册中心
    address: nacos://192.168.1.8:8848
    # 关闭注册中心是否启动的相关检查,false表示不检查注册中心是否启动，就不会报错
    check: true
  provider:
    timeout: 10000
  cloud:
    subscribed-services: spring-provider
```

### Java代码

1. 当 @DubboService 配置了 version ，那么消费者也要进行配置，否则映射不上
2. @EnableDubbo，注解需要在Application上配置。（这个不确定）

```java
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
```



## Consumer（消费者）

### yml 配置

```yml
dubbo:
  application:
    qos-enable: false
    qos-accept-foreign-ip: false
    name: ${spring.application.name}
  consumer:
    timeout: 600000
    check: true  # 关闭订阅服务是否启动的检查【检查时，没有服务提供者会报错】
  protocol:    # Dubbo 服务暴露的协议配置，其中子属性 name 为协议名称，port 为协议端口（ -1 表示自增端口，从 20880 开始）
    name: dubbo
    port: -1  #dubbo协议缺省端口为20880，rmi协议缺省端口为1099，http和hessian协议缺省端口为80；如果没有配置port，则自动采用默认端口，如果配置为-1，则会分配一个没有被占用的端口。Dubbo 2.4.0+，分配的端口在协议缺省端口的基础上增长，确保端口段可控
  registry:
    #其中前缀spring-cloud说明：挂载到 Spring Cloud注册中心
    address: nacos://192.168.1.8:8848
    # address: spring-cloud://localhost  #dubbo服务注册端口，注册中心服务器地址，如果地址没有端口缺省为9090，同一集群内的多个地址用逗号分隔，如：ip:port,ip:port
    username: nacos
    password: nacos
  cloud:
    # 订阅服务的服务名称，一般是application name
    subscribed-services: spring-provider
```

### Java 代码

早期使用 @Reference 注解，但是在2.7版本后使用 @DubboReference 注解。

```java
@DubboReference(version = "1.0")
private HelloService helloService;
```