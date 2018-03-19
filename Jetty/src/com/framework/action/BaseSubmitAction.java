package com.framework.action;

import com.framework.context.Context;

public interface BaseSubmitAction extends BaseAction{
	public abstract void prepare(Context context) throws Exception;
	public abstract void submit(Context context) throws Exception;
}