package com.framework.config;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.framework.chain.Chain;
import com.framework.context.Context;

public class ActionImpl {
	private String classBeanId;//调用Action类 - 在bean中的id
	private String methodName="execute";//调用的方法名
	private LinkedHashMap<String, String> fields;
	private HashMap<String, Map<String, String>> channels;;//返回结果页面
	private Object action;//要调用的Action对象
	private Chain chain;//责任链
	private String result;//返回结果页
	
	public String getClassBeanId() {
		return classBeanId;
	}
	public void setClassBeanId(String classBeanId) {
		this.classBeanId = classBeanId;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public LinkedHashMap<String, String> getFields() {
		return fields;
	}
	public void setFields(LinkedHashMap<String, String> fields) {
		this.fields = fields;
	}
	public HashMap<String, Map<String, String>> getChannels() {
		return channels;
	}
	public void setChannels(HashMap<String, Map<String, String>> channels) {
		this.channels = channels;
	}
	public Object getAction() {
		return action;
	}
	public void setAction(Object action) {
		this.action = action;
	}
	public Chain getChain() {
		return chain;
	}
	public void setChain(Chain chain) {
		this.chain = chain;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
}
