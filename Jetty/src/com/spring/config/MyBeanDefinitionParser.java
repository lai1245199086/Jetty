package com.spring.config;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Element;

public class MyBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {
	protected Class getBeanClass(Element element) {
		return DataSourceInfo.class;
	}

	protected void doParse(Element element, BeanDefinitionBuilder bean) {
		String driverClassName = element.getAttribute("driverClassName");
		bean.addPropertyValue("driverClassName", driverClassName);

		String url = element.getAttribute("url");
		bean.addPropertyValue("url", url);

		String username = element.getAttribute("username");
		bean.addPropertyValue("username", username);

		String password = element.getAttribute("password");
		bean.addPropertyValue("password", password);
	}
}
