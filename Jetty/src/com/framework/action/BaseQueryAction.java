package com.framework.action;

import com.framework.context.Context;

public interface BaseQueryAction extends BaseAction{
	public abstract void query(Context context) throws Exception;
}