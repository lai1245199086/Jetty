package com.cll.action;

import java.util.HashMap;
import java.util.Map;

import com.framework.action.IbsSubmitAction;
import com.framework.context.Context;

public class ErrorHandlerAction extends IbsSubmitAction{
	
	@Override
	public void prepare(Context context) throws Exception {
		log.info("异常处理");
		
	}

	@Override
	public void submit(Context context) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
