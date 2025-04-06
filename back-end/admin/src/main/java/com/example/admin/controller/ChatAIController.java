package com.example.admin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/admin/chatAI")
@RequiredArgsConstructor
@Tag(name = "GPT对话相关")
public class ChatAIController {

    private final ChatClient chatClient;

    @GetMapping(produces = "text/html;charset=UTF-8")
    @Operation(summary = "GPT对话(流式输出)")
    public Flux<String> chat(@RequestParam("prompt") String prompt) {
        return chatClient.prompt()
                .user(prompt)
                .stream()
                .content()
                .doOnNext(System.out::println);
    }



    @GetMapping(value = "/sensor-stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Map<String, Double>> getSensorStream() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(tick -> Map.of(
                        "temperature", generateRandomTemperature(),
                        "humidity", generateRandomHumidity()
                ))
                .doOnNext(sensorData -> System.out.println("传感器读数: " + sensorData));
    }

    private double generateRandomHumidity() {
        Random random = new Random();
        return random.nextDouble() * 100; // 生成 0% 到 100% 的湿度
    }

    // 模拟生成随机温度值（-20°C 到 50°C）
    private double generateRandomTemperature() {
        Random random = new Random();
        return -20 + random.nextDouble() * 70;
    }
}
