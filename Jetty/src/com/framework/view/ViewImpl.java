package com.framework.view;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


public class ViewImpl implements View ,ApplicationContextAware {
	protected Log log = LogFactory.getLog(this.getClass()) ;
	private ApplicationContext applicationContext;
	@Override
	public void execute(String result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		int n = result.indexOf(",")==-1?0:result.indexOf(",")+1;
		result = result.substring(n);
		ViewEnum viewEnum;
		try{
			viewEnum = ViewEnum.valueOf(result);
		}catch(IllegalArgumentException e){
			viewEnum = ViewEnum.jsp;
		}
		View view; 
		switch (viewEnum) {
		case json:
			view = applicationContext.getBean(JsonView.class);
			break;
		case stream:
			view = applicationContext.getBean(StreamView.class);
			break;
		default:
			view = applicationContext.getBean(JspView.class);
			break;
		}
		view.execute(result, request, response);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
