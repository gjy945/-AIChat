package com.example.security.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LoginResponseVO {

    @Schema(description = "token信息")
    private TokenInfoVO tokenInfoVO;

    @Schema(description = "用户昵称")
    private String nickName;

    @Schema(description = "用户头像")
    private String avatar;
}
