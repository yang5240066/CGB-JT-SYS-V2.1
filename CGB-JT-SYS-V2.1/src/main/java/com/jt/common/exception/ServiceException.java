package com.jt.common.exception;
/**
 * 自定义业务异常
 * 1)后台可以更好的定位错误
 * 2)更好实现与用户交互
 * @author UID
 */

public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 8029523183323748146L;

	public ServiceException() {
		super();
		
	}

	public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public ServiceException(String message) {
		super(message);
		
	}

	public ServiceException(Throwable cause) {
		super(cause);
		
	}
	
	
}
