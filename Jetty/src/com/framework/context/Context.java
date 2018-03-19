package com.framework.context;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.framework.config.ActionImpl;

public interface Context {
	public Object getData(String string);
	public void setData(String string,Object object);
	public void setStatus(String status);
	public String getStatus();
	public String getChannel();
	public void setLocale(Locale locale);
	public Locale getLocale();
	public void setChannel(String channel);
	public Map<String, Object> getDataMap();
	public ActionImpl getActionConfig();
	public void setActionConfig(ActionImpl actionConfig);
	public HttpSession getSession();
	public void setSesstion(HttpSession session);
	public HttpServletRequest getRequest();
	public void setRequest(HttpServletRequest request);
	public String getTransactionId();
	public void setTransactionId(String transactionId);
}
