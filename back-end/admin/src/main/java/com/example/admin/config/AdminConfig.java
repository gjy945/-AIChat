package com.example.admin.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


/**
 * 配置文件
 */
@Data
@Component
@PropertySource("classpath:admin.properties")
@ConfigurationProperties(prefix = "admin")
public class AdminConfig {

	/**
	 * 数据中心ID
	 */
	private Integer datacenterId;

	/**
	 * 终端ID
	 */
	private Integer workerId;

}
