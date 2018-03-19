package com.cll.action;

import java.util.List;
import java.util.Map;

import com.framework.action.IbsQueryAction;
import com.framework.context.Context;

public class HelloAction extends IbsQueryAction{
	public void hello(Context context) {
		context.setData("UserId", "superleo " + context.getData("UserId"));
		log.info("hello 方法被执行");
		List<Map<String,Object>> users  = sqlMap.selectList("users.getAllUser");
		System.out.println(users);
		for (Map<String, Object> map : users) {
			System.out.print(map.get("Id")+"\t");
			System.out.print(map.get("UserName")+"\t");
			System.out.print(map.get("Password")+"\t");
			System.out.print("\r");
		}
	}
	
	@Override
	public void query(Context context) {
		log.info("query 方法被执行");
	}
}
