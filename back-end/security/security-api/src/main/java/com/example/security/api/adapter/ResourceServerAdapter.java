package com.example.security.api.adapter;

import com.example.security.common.adapter.DefaultAuthConfigAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class ResourceServerAdapter extends DefaultAuthConfigAdapter {


    @Override
    public List<String> pathPatterns() {  // 拦截路径拦截所有 /p/** 路径,用户端都/p/**的请求
        return Collections.singletonList("/p/*");
    }

    public static final List<String> EXCLUDE_PATH = Arrays.asList(
            "/webjars/**",
            "/swagger/**",
            "/v3/api-docs/**",
            "/doc.html",
            "/swagger-ui.html",
            "/swagger-resources/**",
            "/file/**",
            "/common/**",
            "/login/**",
            "/hi"
    );

    @Override
    public List<String> excludePathPatterns() {
        return EXCLUDE_PATH;
    }
}
