package com.example.common.constant;

public interface ChatAICacheNames {

    /**
     * chatAI 相关key
     */
    String CHAT_PREFIX = "chatAI:";

    /**
     * chatAI保存历史列表
     */
    String HISTORY_PREFIX = CHAT_PREFIX + "history:";

    /**
     * chatAI保存历史聊天记录
     */
    String CHAT_HISTORY_PREFIX = CHAT_PREFIX + "chatHistory:";
}
