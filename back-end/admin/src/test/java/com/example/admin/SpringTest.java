package com.example.admin;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * <p>
 * 默认行为：默认情况下，Spring Boot 测试 (@SpringBootTest)
 * 使用 WebEnvironment.MOCK，它不会启动真实的 Servlet 容器（如 Tomcat），而是创建一个模拟的 Web 环境。
 * 使用 RANDOM_PORT：当指定 webEnvironment = WebEnvironment.RANDOM_PORT 时，
 * Spring Boot 会启动一个真实的 Servlet 容器（如 Tomcat），并在随机端口上监听请求。
 * ServerEndpointExporter 需要真实的 Servlet 容器
 * ServerEndpointExporter 是 Spring 提供的用于注册 WebSocket 端点的工具类，它依赖于 Servlet 容器提供的 ServerContainer。
 * 如果测试环境中没有启动真实的 Servlet 容器（如使用默认的 MOCK 模式），ServerContainer 将不存在，导致 Bean 初始化失败。
 * </p>
 *
 * WebEnvironment 的三种模式
 * 模式	        行为
 * MOCK (默认)	使用模拟的 Servlet 环境（如 MockMvc），不启动真实容器。
 * RANDOM_PORT	启动真实容器，并在随机端口上运行。
 * DEFINED_PORT	启动真实容器，并使用配置文件中定义的端口（如 service.port）。
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringTest {

    @Test
    void contextLoads() {
        System.out.println("测试");
    }

}
