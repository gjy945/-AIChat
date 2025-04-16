package com.example.admin.controller;

import com.example.common.chat.ChatSession;
import com.example.common.chat.ChatSessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/admin/chatAI")
@RequiredArgsConstructor
@Tag(name = "GPT对话相关")
public class ChatAIController {

    private final ChatClient chatClient;

    private final ChatSessionService chatSessionService;

    @RequestMapping(produces = "text/html;charset=UTF-8")
    @Operation(summary = "GPT对话(流式输出)")
    public Flux<String> chat(@RequestParam("prompt") String prompt, String chatId) {
        // 1. 保存会话id
        ChatSession session = new ChatSession();
        session.setSessionId(chatId);
        session.setSessionName("普通对话");
        chatSessionService.saveSession(session, "chat");
        // 2. 请求模型
        return chatClient.prompt()
                .user(prompt)
                .advisors(a -> a.param(AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY, chatId))
                .stream()
                .content();
    }
}
