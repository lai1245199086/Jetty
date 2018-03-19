package com.framework.token;


import com.framework.action.IbsQueryAction;
import com.framework.context.Context;

public class CreateToken extends IbsQueryAction{

	@Override
	public void query(Context context) throws Exception {
		String token = TokenProccessor.getInstance().makeToken();//创建token
		log.debug("生成 token >" + token);
		context.getSession().setAttribute("_token", token);//在服务器使用session保存token(令牌)
	}
}