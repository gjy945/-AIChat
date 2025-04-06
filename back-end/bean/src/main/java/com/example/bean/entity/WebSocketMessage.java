package com.example.bean.entity;

import com.example.bean.enums.MessageType;
import com.example.bean.enums.ChatType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Schema(name = "WebSocket消息实体类")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebSocketMessage {
    @Schema(name = "消息聊天类型")
    private ChatType chatType;
    @Schema(name = "消息类型")
    private MessageType messageType;
    @Schema(name = "消息内容")
    private List<String> content;
    @Schema(name = "消息发送者ID")
    private String senderId;
    @Schema(name = "消息接收者ID")
    private List<String> receiverId; // 仅当类型为 SINGLE_SEND 时有效
    @Schema(name = "消息群组ID")
    private String groupId;
    @Schema(name = "消息元数据 如文件名、文件大小、文件类型等")
    private List<FileUpload> metadata; // 其他元数据 如文件名、文件大小、文件类型等
    @Schema(name = "文件访问路径")
    private List<String> filePath;


}
