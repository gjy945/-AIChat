package com.example.common.config;

import com.example.common.chat.RedisChatMemory;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * SpringAI配置
 */
@Component
@RequiredArgsConstructor
public class SpringAIConfig {

//    @Bean
//    public ChatMemory chatMemory() {
//        return new InMemoryChatMemory(); // 配置ChatMemory存储到redis中
//    }

    @Bean
    public ChatClient chatClient(OllamaChatModel model, RedisChatMemory chatMemory) {
        return ChatClient
                .builder(model)
                .defaultAdvisors(new SimpleLoggerAdvisor(),
                        new MessageChatMemoryAdvisor(chatMemory)) // 配置日志Advisor
//                .defaultSystem("你是一个农作物种植AI助手,叫刘雨豪,你只能回答关于农作物种植的问题,不能回答其他问题")
                .build();
    }
}
