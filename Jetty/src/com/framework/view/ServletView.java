package com.framework.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ServletView implements View{
	protected Log log = LogFactory.getLog(this.getClass()) ;
	@Override
	public void execute(String result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("Result Servlet >> "+result);
	}
	
}
