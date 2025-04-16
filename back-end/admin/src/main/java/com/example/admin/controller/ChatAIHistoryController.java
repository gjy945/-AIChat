package com.example.admin.controller;

import com.example.bean.vo.MessageVo;
import com.example.common.chat.ChatSession;
import com.example.common.chat.ChatSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/chatAI/history")
public class ChatAIHistoryController {

    private final ChatSessionService chatSessionService;

    private final ChatMemory chatMemory;

    @GetMapping("/{type}")
    public Object getChatIds(@PathVariable("type") String type) {

        return Optional.ofNullable(chatSessionService.getSessions(type)).stream().flatMap(Collection::stream)
               .map(ChatSession::getSessionId)
               .toList();
    }

    @GetMapping("/{type}/{chatId}")
    public List<MessageVo> getChatHistory(@PathVariable("type") String type, @PathVariable("chatId") String chatId) {
        List<Message> messages = chatMemory.get(chatId, Integer.MAX_VALUE);
        if (messages == null) {
            return List.of();
        }
        return messages.stream().map(MessageVo::new).toList();
    }

}
