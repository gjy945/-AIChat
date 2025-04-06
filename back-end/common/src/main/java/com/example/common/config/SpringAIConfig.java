package com.example.common.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * SpringAI配置
 */
@Component
public class SpringAIConfig {

    @Bean
    public ChatClient chatClient(OllamaChatModel model){
        return ChatClient
                .builder(model)
                //.defaultSystem("你是一个农作物种植AI助手,叫刘雨豪,你只能回答关于农作物种植的问题,不能回答其他问题")
                .build();
    }
}
