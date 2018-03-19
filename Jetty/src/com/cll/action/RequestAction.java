package com.cll.action;

import com.framework.action.IbsSubmitAction;
import com.framework.context.Context;

public class RequestAction extends IbsSubmitAction{

	@Override
	public void prepare(Context context) throws Exception {
		//throw new RuntimeException("34easfs男男女女");
		//int i = 1/0;
		//String[] str = {"AAA"};
		//System.out.println(str[1]);
		String string = null;
		String[] str = string.split(",");
		
	}

	@Override
	public void submit(Context context) throws Exception {
		context.setData("Text", context.getData("UserId")+"，你有七十二变！");
		context.setStatus("hello");
		log.info("execute 方法被执行");
	}

	
}
