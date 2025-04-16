package com.example.common.chat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors; /**
 * 会话
 */
@Data
@Accessors(chain = true)
public class ChatSession {

    @Schema(description = "会话id")
    private String sessionId;

    @Schema(description = "会话名称")
    private String sessionName;
}
