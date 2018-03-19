package com.framework.chain;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.framework.context.Context;

public class ChainImpl implements Chain{
	protected Log log = LogFactory.getLog(this.getClass()) ;
	private LinkedHashMap<String,Command> commands;
	
	@Override
	public void execute(Context context)  throws Exception{
		log.info("----------责任链执行开始-------");
		for (Map.Entry<String, Command> entry : commands.entrySet()) {
			entry.getValue().execute(context);
		}
		log.info("----------责任链执行结束-------");
	}
	@Override
	public LinkedHashMap<String, Command> getCommands() {
		return commands;
	}
	@Override
	public void setCommands(LinkedHashMap<String, Command> commands) {
		this.commands = commands;
	}
	
}
