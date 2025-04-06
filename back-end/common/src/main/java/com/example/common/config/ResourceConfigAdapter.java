package com.example.common.config;

import com.example.common.json.JacksonObjectMapper;
import com.example.common.properties.FileProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 配置类，注册web层相关组件
 */
@Configuration
@Slf4j
@RequiredArgsConstructor
public class ResourceConfigAdapter implements WebMvcConfigurer {


    private final FileProperties fileProperties;

    /**
     * 设置静态资源映射
     *
     * @param registry
     */
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        // 文件下载资源映射
        registry.addResourceHandler(fileProperties.getPath() + "/**")
                .addResourceLocations("file:" + fileProperties.getSavePath() + "/" + fileProperties.getPath() + "/");
    }

    /**
     * 扩展消息转换器
     *
     * @param converters
     */
//    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
//        log.info("扩展消息转换器 ...");
//        // 创建消息转换器对象
//        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
//        // 设置对象转换器，底层使用Jackson将Java对象转为json
//        messageConverter.setObjectMapper(new JacksonObjectMapper());
//        // 将上面的消息转换器对象追加到mvc框架的转换器集合中
//        converters.add(0, messageConverter);
//    }
    /**
     * 扩展消息转换器,将日期类型从列表转换为时间戳
     * 这个是导致knife4j不能正常显示的罪魁祸首,特别要注意添加的位置
     * Swagger 的 OpenAPI 规范要求日期时间格式为 yyyy-MM-dd'T'HH:mm:ss.SSSZ，
     * 而你的 JacksonObjectMapper 强制将 LocalDateTime 格式化为 yyyy-MM-dd HH:mm，
     * 导致 Knife4j 生成的文档不符合规范，前端无法解析。
     * @param converters 消息转换器列表
     */

    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        jackson2HttpMessageConverter.setObjectMapper(new JacksonObjectMapper());
        converters.add(converters.size()-1,jackson2HttpMessageConverter);
    }
}
