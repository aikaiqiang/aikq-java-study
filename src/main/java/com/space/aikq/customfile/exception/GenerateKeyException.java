package com.space.aikq.customfile.exception;

/**
 *  E
 * @author aikq
 * @date 2019年02月25日 10:57
 */
public class GenerateKeyException extends RuntimeException {
	public GenerateKeyException() {
		super();
	}

	public GenerateKeyException(String message) {
		super(message);
	}

	public GenerateKeyException(String message, Throwable cause) {
		super(message, cause);
	}

	public GenerateKeyException(Throwable cause) {
		super(cause);
	}

	protected GenerateKeyException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * 重写fillInStackTrace 业务异常不需要堆栈信息，提高效率.
	 * @see Throwable#fillInStackTrace()
	 */
	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}
}
