package com.example.bean.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.ai.chat.messages.Message;

@NoArgsConstructor
@Data
public class MessageVo {
    private String role;
    private String content;

    public MessageVo(Message message){
        switch (message.getMessageType()){
            case USER:
                role = "user";
                break;
            case ASSISTANT:
                role = "assistant";
                break;
            case SYSTEM:
                role = "system";
                break;
            case TOOL:
                role = "function";
                break;
            default:
                role = "";
                break;
        }
        this.content = message.getText();
    }
}
