package com.framework.service;

public abstract class XmlParser implements Parser{
	private String endTag;
	public Object parserMsg(Object object,Object head) {
		return parserXmlMsg(object,head);
	}
	public abstract Object parserXmlMsg(Object object,Object head);
	public String getEndTag() {
		return endTag;
	}
	public void setEndTag(String endTag) {
		this.endTag = endTag;
	}
}
