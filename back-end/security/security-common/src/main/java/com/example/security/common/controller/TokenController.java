package com.example.security.common.controller;
import cn.hutool.core.bean.BeanUtil;
import com.example.common.response.ServerResponseEntity;
import com.example.security.common.bo.TokenInfoBO;
import com.example.security.common.dto.RefreshTokenDTO;
import com.example.security.common.manager.TokenStore;
import com.example.security.common.vo.TokenInfoVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "token")
public class TokenController {

    @Autowired
    private TokenStore tokenStore;

    @PostMapping("/token/refresh")
    public ServerResponseEntity<TokenInfoVO> refreshToken(@Valid @RequestBody RefreshTokenDTO refreshTokenDTO) {
        TokenInfoBO tokenInfoServerResponseEntity = tokenStore
                .refreshToken(refreshTokenDTO.getRefreshToken());
        return ServerResponseEntity
                .success(BeanUtil.copyProperties(tokenInfoServerResponseEntity, TokenInfoVO.class));
    }

}
