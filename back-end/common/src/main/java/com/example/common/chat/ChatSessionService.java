package com.example.common.chat;

import com.alibaba.fastjson.JSON;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xtwang
 * @des 会话服务
 * @date 2025/2/11 下午3:10
 */
@Service
@RequiredArgsConstructor
public class ChatSessionService {
    public static final String CHAT_SESSION_PREFIX = "chat_session:";
    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 保存会话
     *
     * @param chatSession 会话
     */
    public void saveSession(ChatSession chatSession, String userId) {
        String key = CHAT_SESSION_PREFIX + userId;
        stringRedisTemplate.opsForList().leftPush(key, JSON.toJSONString(chatSession));
    }

    /**
     * 获取会话列表
     *
     * @return 会话列表
     */
    public List<ChatSession> getSessions(String userId) {
        String key = CHAT_SESSION_PREFIX + userId;
        List<String> strings = stringRedisTemplate.opsForList().range(key, 0, -1);
        if (strings != null) {
            return strings.stream().map(s -> JSON.parseObject(s, ChatSession.class)).toList();
        }
        return List.of();
    }
}
