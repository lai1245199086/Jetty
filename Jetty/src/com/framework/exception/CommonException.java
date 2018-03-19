package com.framework.exception;

import org.apache.log4j.Logger;

public class CommonException  extends Exception{
	private static final long serialVersionUID = 1L;
	protected Logger logger = Logger.getLogger(this.getClass());
	private String errorCode = "9999";
	private Object[] paramArgs = null;
	private String messageKey = "unkonw";
	
	public CommonException(String messageKey,String errorCode) {
		super(messageKey);
		this.errorCode = errorCode;
		this.messageKey = messageKey;
	}
	public CommonException(String messageKey,Object[] paramArgs,String errorCode) {
		super(messageKey);
		this.errorCode = errorCode;
		this.messageKey = messageKey;
		this.paramArgs = paramArgs;
	}
	public CommonException(String messageKey,Object[] paramArgs) {
		super(messageKey);
		this.messageKey = messageKey;
		this.paramArgs = paramArgs;
	}
	
	public CommonException(String messageKey) {
		super(messageKey);
		this.messageKey = messageKey;
	}
	
	public CommonException() {
        super();
    }
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public Object[] getParamArgs() {
		return paramArgs;
	}
	public void setParamArgs(Object[] paramArgs) {
		this.paramArgs = paramArgs;
	}
	public String getMessageKey() {
		return messageKey;
	}
	public void setMessageKey(String messageKey) {
		this.messageKey = messageKey;
	}
	
}
