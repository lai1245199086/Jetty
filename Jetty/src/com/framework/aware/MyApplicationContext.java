package com.framework.aware;

import org.springframework.beans.factory.BeanNameAware;

public class MyApplicationContext implements BeanNameAware{
	private String beanName;
	//注入的beanName即为MyApplicationContext在BeanFactory配置中的名字（根据情况是id或者name）
    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
        System.out.println("MyApplicationContext beanName:"+beanName);
    }
}
