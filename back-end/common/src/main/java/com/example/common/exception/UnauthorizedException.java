package com.example.common.exception;

/**
 * 未授权异常
 */
public class UnauthorizedException extends BaseException {

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(Integer code, String message) {
        super(message);
    }
}