package com.example.common.utils;


import jakarta.servlet.http.HttpServletResponse;

public class WebUtils {

    /**
     * 将字符串渲染到客户端
     * @param response 渲染对象
     * @param string 带渲染字符串
     * @return null
     */

    public static String renderString(HttpServletResponse response, String string) {
        try {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
