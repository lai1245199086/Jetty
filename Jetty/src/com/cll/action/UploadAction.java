package com.cll.action;

import java.util.HashMap;
import java.util.Map;

import com.framework.action.IbsSubmitAction;
import com.framework.context.Context;

public class UploadAction extends IbsSubmitAction{
	
	@Override
	public void prepare(Context context) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void submit(Context context) {
		
	}
	
	public void upload(Context context){
		log.info("文件上传");
		context.setData("success", "上传成功");
		context.setData("Array", new Integer[]{1,2,3,4});
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("key1", 21321);
		map.put("string", "cll");
		context.setData("Map", map);
	}
	
}
