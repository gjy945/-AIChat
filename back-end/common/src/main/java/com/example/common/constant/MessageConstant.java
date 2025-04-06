package com.example.common.constant;

import java.util.Map;

/**
 * 信息提示常量类
 */
public class MessageConstant {

    // 身份验证相关的错误消息
    public static final String PASSWORD_ERROR = "密码错误";
    public static final String ACCOUNT_NOT_FOUND = "账号不存在";
    public static final String ACCOUNT_LOCKED = "账号被锁定";
    public static final String ALREADY_EXISTS = "账号已存在";
    public static final String USER_NOT_LOGIN = "用户未登录";
    public static final String LOGIN_FAILED = "登录失败";
    public static  final String REGISTER_FAILED="注册失败";

    // 文件操作相关的错误消息
    public static final String UPLOAD_FAILED = "文件上传失败";
    public static final String DOWNLOAD_FAILED = "文件下载失败";
    public static final String FILE_NOT_FOUND = "文件未找到";
    public static final String FILE_ACCESS_DENIED = "文件访问被拒绝";

    // 数据操作相关的错误消息
    public static final String DATA_NOT_FOUND = "数据未找到";
    public static final String DATA_ALREADY_EXISTS = "数据已存在";
    public static final String DATA_UPDATE_FAILED = "数据更新失败";
    public static final String DATA_DELETE_FAILED = "数据删除失败";

    // 授权相关的错误消息
    public static final String ACCESS_DENIED = "访问被拒绝";
    public static final String PERMISSION_DENIED = "权限不足";

    // 通用错误消息
    public static final String UNAUTHORIZED = "未授权";
    public static final String FORBIDDEN = "禁止访问";
    public static final String INTERNAL_SERVER_ERROR = "内部服务器错误";
    public static final String BAD_REQUEST = "无效请求";
    public static final String NOT_IMPLEMENTED = "功能未实现";
    public static final String METHOD_NOT_ALLOWED = "方法不允许";
    public static final String REQUEST_TIMEOUT = "请求超时";
    public static final String SERVICE_UNAVAILABLE = "服务不可用";
    public static final String UNKNOWN_ERROR = "未知错误";

    // 其他错误消息
    public static final String PASSWORD_EDIT_FAILED = "密码修改失败";
    public static final String EMAIL_SEND_FAILED = "邮件发送失败";
    public static final String EMAIL_NOT_VERIFIED = "邮箱未验证";
    public static final String PHONE_NUMBER_NOT_VERIFIED = "手机号未验证";
    public static final String INVALID_TOKEN = "无效的令牌";
    public static final String TOKEN_EXPIRED = "令牌已过期";
    public static final String SESSION_EXPIRED = "会话已过期";
    public static final String AUTHENTICATION_REQUIRED = "需要认证";
    public static final String CAPTCHA_INVALID = "验证码无效";
    public static final String CAPTCHA_EXPIRED = "验证码已过期";
    public static final String TOO_MANY_REQUESTS = "请求过多";
    public static final String RATE_LIMIT_EXCEEDED = "超出速率限制";
    public static final String RESOURCE_NOT_FOUND = "资源未找到";
    public static final String INVALID_INPUT = "无效输入";
    public static final String INVALID_PARAMETER = "无效参数";
    public static final String PARAMETER_MISSING = "缺少参数";
    public static final String INVALID_FORMAT = "格式无效";
    public static final String CONNECTION_REFUSED = "连接被拒绝";
    public static final String CONNECTION_TIMEOUT = "连接超时";
    public static final String NETWORK_ERROR = "网络错误";
    public static final String SYSTEM_MAINTENANCE = "系统维护中";
    public static final String FEATURE_DISABLED = "功能已禁用";
    public static final String CONFIGURATION_ERROR = "配置错误";
    public static final String DATABASE_ERROR = "数据库错误";
    public static final String QUERY_ERROR = "查询错误";
    public static final String TRANSACTION_ERROR = "事务处理错误";
    public static final String CACHE_MISS = "缓存未命中";
    public static final String CACHE_OPERATION_FAILED = "缓存操作失败";
    public static final String QUEUE_OPERATION_FAILED = "队列操作失败";
    public static final String MESSAGE_SEND_FAILED = "消息发送失败";
    public static final String MESSAGE_RECEIVE_FAILED = "消息接收失败";
    public static final String MESSAGE_PROCESSING_FAILED = "消息处理失败";
    public static final String NULL_POINT_ERROR = "空指针异常";
}
