package com.framework.handler;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;

import com.framework.context.Context;

public interface ExceptionHandler extends ApplicationContextAware,MessageSourceAware {
	
	public void execute(Context context,Exception e);
	public String parseLocalizedMessage(Context context,Exception e);
}
