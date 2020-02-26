package site.yl.template.share.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

/**
 * 业务异常类
 */
public class BusinessException extends RuntimeException {

	private final Logger logger= LoggerFactory.getLogger(BusinessException.class);

	private StringBuilder msgBuilder = new StringBuilder();

	private String code;

	public BusinessException(BeCase exceptionCase) {
		msgBuilder.append(exceptionCase.getMessage()).append(" ");
		this.code = exceptionCase.getCode();
	}

	public static BusinessException exception(BeCase cause) {
		return new BusinessException(cause);
	}

	public String getCode() {
		return code;
	}

	@Override
	public String getMessage() {
		return msgBuilder.toString();
	}

	@Override
	public String toString() {
		return this.code + ":" + getMessage();
	}

	public BusinessException restMsg(String mod, Object... obj) {
		msgBuilder = new StringBuilder(format(mod, obj));
		return this;
	}

	public BusinessException appendMsg(String mod, Object... obj) {
		msgBuilder.append(format(mod, obj));
		return this;
	}

	private String format(String pattern, Object... obj) {
		try {
			return MessageFormat.format(pattern, obj);
		} catch (IllegalArgumentException e) {
			logger.debug("happened exception when format exception msg:", e);
			return MessageFormat.format("(lose some exception message,cause by:{0}(pattern={1}))",
					e.getMessage(),pattern);
		}
	}

	/**
	 * 业务异常情景
	 */
	public static class BeCase {

		private final String code;
		private String message;

		public BeCase(String code, String message) {
			this.code = code;
			this.message = message;
		}

		public String getCode() {
			return code;
		}

		public String getMessage() {
			return this.message;
		}

	}

}