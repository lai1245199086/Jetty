package com.framework.chain;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.framework.context.Context;

public class LoginCheckCommand implements Command{
	protected Log log = LogFactory.getLog(this.getClass()) ;
	@Override
	public void execute(Context context) throws Exception{
		
		log.info("在这里做登录校验");
	}
}
