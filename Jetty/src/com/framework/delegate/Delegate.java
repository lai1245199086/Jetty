package com.framework.delegate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
/**
 * 委派模式：可以让方法传递方法
 *
 */
public class Delegate {
    private Class<?> c=null;//委托类
    private String method=null;//委托执行的方法
    private Class<?>[] paramsType=null;//参数类型
    private Object[] params;//参数值
    
    public Delegate(Class<?> c,String method,Class<?>[] paramsType,Object[] params)
    {
        this.c=c;
        this.method=method;
        this.paramsType=paramsType;
        this.params=params;
    }
    
    public void invoke() throws NoSuchMethodException, SecurityException, InstantiationException,IllegalAccessException,
    	IllegalArgumentException, InvocationTargetException
    {
        Method m = c.getDeclaredMethod(method, paramsType);
        Object o = c.newInstance();
        m.invoke(o, params);
    }
}
