package com.framework.context;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.framework.config.ActionImpl;
public class ContextImpl implements Context{
	protected Log log = LogFactory.getLog(this.getClass()) ;
	private Map<String, Object> dataMap;//可以使用ConcurrentHashMap<String, Object>
	private String status = "success";
	private String channel = "http";
	private String transactionId = "";
	private ActionImpl actionConfig;
	private Locale locale;
	private HttpSession session;
	private HttpServletRequest request;
	
	ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<Map<String, Object>>(){
		public Map<String, Object> initialValue() {
			log.info("ThreadLocal中获取dataMap");
            return new HashMap<String, Object>();  
        } 
	};
	
	public ContextImpl() {
		dataMap = threadLocal.get();//为每个线程都独立的new一个dataMap对象
	}
	@Override
	public Object getData(String string) {
		return dataMap.get(string);
	}
	@Override
	public void setData(String string, Object object) {
		dataMap.put(string, object);
	}
	@Override
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String getStatus() {
		return status;
	}
	@Override
	public Map<String, Object> getDataMap() {
		return this.dataMap;
	}
	@Override
	public String getChannel() {
		return this.channel;
	}
	@Override
	public void setChannel(String channel) {
		this.channel = channel;
	}
	@Override
	public ActionImpl getActionConfig() {
		return actionConfig;
	}
	@Override
	public void setActionConfig(ActionImpl actionConfig) {
		this.actionConfig = actionConfig;
	}
	@Override
	public HttpSession getSession() {
		return this.session;
	}
	@Override
	public void setSesstion(HttpSession session) {
		this.session = session;
	}
	@Override
	public HttpServletRequest getRequest() {
		return request;
	}
	@Override
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	public Locale getLocale() {
		return locale;
	}
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	
	
}
