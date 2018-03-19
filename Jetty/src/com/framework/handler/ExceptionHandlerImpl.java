package com.framework.handler;

import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.NoSuchMessageException;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.framework.context.Context;
import com.framework.exception.ValidationException;
import com.framework.util.StringUtil;

public class ExceptionHandlerImpl implements ExceptionHandler{
	protected Log log = LogFactory.getLog(this.getClass());
	// extends ApplicationContextAware,MessageSourceAware 
	private ApplicationContext applicationContext;
	private MessageSource messageSource;
	private MessageSource dictMessageSource;
	private String message = "unknown.error";
	
	public String parseLocalizedMessage(Context context,Exception e) {
		//SessionLocaleResolver localeResolver = applicationContext.getBean(SessionLocaleResolver.class);
		//Locale locale = localeResolver.resolveLocale(context.getRequest());
		Locale locale = context.getLocale();
		if (e instanceof ValidationException) {
			ValidationException exp = (ValidationException)e;
			Object[] paramArgs = exp.getParamArgs();
			String messageKey = exp.getMessageKey();
			try {
				//paramArgs = new Object[]{this.messageSource.getMessage(entry.getKey(),null, locale)};
				if (null != paramArgs) {
					for (int i =0; i< paramArgs.length ;i++) {
						try {
							paramArgs[i] = (this.dictMessageSource.getMessage((String)paramArgs[i], null, locale));
						} catch (Exception e2) {
							log.info(paramArgs[i]+" does not config in the file 'dict_zh_CN.properties'");
							continue;
						}
					}
				}
				message = this.messageSource.getMessage( messageKey, paramArgs, locale);
			} catch (NoSuchMessageException en) {
				if (null!=paramArgs) {
					message = "["+paramArgs.toString()+"] "+ messageKey; 
				}else {
					message =  messageKey; 
				}
			}
			log.info("自定义异常信息 >> " + message);
		}else if (e instanceof Exception) {
			message = e.getMessage();
			log.info("非自定义异常信息 >> " + message);
			if (StringUtil.isEmpty(message)) {
				message = "unknown.error";
				message = this.messageSource.getMessage( message, null, locale);
			}
		}
		return message;
	}
	@Override
	public void execute(Context context, Exception e) {
		RuntimeException runtimeExp= new RuntimeException(message,e);
		throw runtimeExp;
	}
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	public void setDictMessageSource(MessageSource dictMessageSource) {
		this.dictMessageSource = dictMessageSource;
	}
	
}
