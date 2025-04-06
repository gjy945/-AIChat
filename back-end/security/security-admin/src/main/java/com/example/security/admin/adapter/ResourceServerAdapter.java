package com.example.security.admin.adapter;
import com.example.security.common.adapter.DefaultAuthConfigAdapter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * 放行路径
 */
@Component
public class ResourceServerAdapter extends DefaultAuthConfigAdapter {
    public static final List<String> EXCLUDE_PATH = Arrays.asList(
            "/webjars/**",
            "/swagger/**",
            "/v3/api-docs/**",
            "/doc.html",
            "/swagger-ui.html",
            "/swagger-resources/**",
            "/file/**",
            "/common/**",
            "/admin/user/register",
            "/admin/user/login",
            "/admin/chatAI/**"
    );
    @Override
    public List<String> excludePathPatterns() {
        return EXCLUDE_PATH;
    }
}
