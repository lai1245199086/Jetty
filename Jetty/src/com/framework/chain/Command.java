package com.framework.chain;

import com.framework.context.Context;

public interface Command {
	public void execute(Context context) throws Exception;
}