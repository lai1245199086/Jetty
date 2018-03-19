package com.cll.action;

import com.framework.action.IbsQueryAction;
import com.framework.context.Context;

public class GetImgAction extends IbsQueryAction{
	
	@Override
	public void query(Context context) throws Exception {
		log.info("获取图片");
	}
	
	@Override
	public void execute(Context context) throws Exception {
		// TODO Auto-generated method stub
		super.execute(context);
	}
	
}
