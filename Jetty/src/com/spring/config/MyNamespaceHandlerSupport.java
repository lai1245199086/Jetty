package com.spring.config;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

  
public class MyNamespaceHandlerSupport extends NamespaceHandlerSupport  
{  
    @Override  
    public void init()  
    {  
    	//注册用于解析<bf:stop>的解析器
        registerBeanDefinitionParser("datasource", new MyBeanDefinitionParser());
    }  
}  