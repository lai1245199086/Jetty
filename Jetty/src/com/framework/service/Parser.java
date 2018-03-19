package com.framework.service;

public interface Parser {
	public void setEndTag(String endTag);
	public Object parserMsg(Object paramData, Object head);
}
