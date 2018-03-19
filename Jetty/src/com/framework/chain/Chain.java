package com.framework.chain;

import java.util.LinkedHashMap;

public interface Chain extends Command{
	public LinkedHashMap<String,Command> getCommands();
	public void setCommands(LinkedHashMap<String,Command> commands);
}
