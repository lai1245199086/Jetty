package com.framework.service;

import com.framework.exception.CommonException;
import com.framework.exception.CommunicationException;

public interface Transport extends Service{
	public void setCharSet(String charSet);
	public void setPort(int port);
	public void setHost(String host);
	public void setDebug(boolean debug);
	public void setParser(Parser parser);
	public abstract Object submit(Object object) throws CommunicationException,CommonException;
}
