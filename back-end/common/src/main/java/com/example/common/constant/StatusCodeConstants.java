package com.example.common.constant;

/**
 * 状态码常量
 */
public class StatusCodeConstants {

    // --------------- HTTP 状态码 ---------------
    /**
     * 成功
     */
    public static final int HTTP_OK = 200;
    /**
     * 已创建
     */
    public static final int HTTP_CREATED = 201;
    /**
     * 已接受
     */
    public static final int HTTP_ACCEPTED = 202;
    /**
     * 无内容
     */
    public static final int HTTP_NO_CONTENT = 204;

    /**
     * 错误请求
     */
    public static final int HTTP_BAD_REQUEST = 400;
    /**
     * 未授权
     */
    public static final int HTTP_UNAUTHORIZED = 401;
    /**
     * 禁止访问
     */
    public static final int HTTP_FORBIDDEN = 403;
    /**
     * 未找到
     */
    public static final int HTTP_NOT_FOUND = 404;
    /**
     * 方法不允许
     */
    public static final int HTTP_METHOD_NOT_ALLOWED = 405;

    /**
     * 服务器内部错误
     */
    public static final int HTTP_INTERNAL_SERVER_ERROR = 500;
    /**
     * 服务不可用
     */
    public static final int HTTP_SERVICE_UNAVAILABLE = 503;


    // --------------- 自定义业务状态码 ---------------
    /**
     * 业务处理成功
     */
    public static final int BUSINESS_SUCCESS = 1;

    public static final int BUSINESS_ERROR = 0;

}