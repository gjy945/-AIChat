package com.example.common.exception;

import com.example.common.response.ResponseEnum;
import com.example.common.response.ServerResponseEntity;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException{

	/**
	 *
	 */
	private static final long serialVersionUID = -4137688758944857209L;

	/**
	 * http状态码
	 */
	private String code;

	private Object object;

	private ServerResponseEntity<?> serverResponseEntity;

	public BaseException(ResponseEnum responseEnum) {
		super(responseEnum.getMsg());
		this.code = responseEnum.value();
	}
	/**
	 * @param responseEnum
	 */
	public BaseException(ResponseEnum responseEnum, String msg) {
		super(msg);
		this.code = responseEnum.value();
	}

	public BaseException(ServerResponseEntity<?> serverResponseEntity) {
		this.serverResponseEntity = serverResponseEntity;
	}


	public BaseException(String msg) {
		super(msg);
		this.code = ResponseEnum.SHOW_FAIL.value();
	}

	public BaseException(String msg, Object object) {
		super(msg);
		this.code = ResponseEnum.SHOW_FAIL.value();
		this.object = object;
	}

}
