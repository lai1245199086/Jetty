package com.framework.chain;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.framework.context.Context;
/**
 * 委派模式：可以让方法传递方法
 *
 */
public class DelegateCommand implements Command,ApplicationContextAware{
    private Class<?> c=null;//委托类
    private String methodName=null;//委托执行的方法
    private Class<?> paramsType=null;//参数类型
    private Object params;//参数值
    private static ApplicationContext applicationContext;
    
    public DelegateCommand() {
	}

	public DelegateCommand(Class<?> c,String methodName,Class<?> paramsType,Object params)
    {
        this.c=c;
        this.methodName=methodName;
        this.paramsType=paramsType;
        this.params=params;
    }
    
    public void invoke() throws NoSuchMethodException, SecurityException, InstantiationException,IllegalAccessException,
    	IllegalArgumentException, InvocationTargetException
    {
    	Method m = null;
    	try {
    		m = c.getDeclaredMethod(methodName, paramsType);
		} catch (NoSuchMethodException e) {
			m = getSuperDeclaredMethod(c, methodName, paramsType);
		}
        
        Object o = applicationContext.getBean(c);
        m.invoke(o, params);
    }

	@Override
	public void execute(Context context) throws Exception{
//		try {
			this.invoke();
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new RuntimeException("DelegateCommand 执行出错");
//		} 
	}

	public void setC(Class<?> c) {
		this.c = c;
	}

	public void setMethod(String methodName) {
		this.methodName = methodName;
	}

	public void setParamsType(Class<?> paramsType) {
		this.paramsType = paramsType;
	}

	public void setParams(Object params) {
		this.params = params;
	}
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	
    /** 
     * 循环向上转型, 获取对象的 DeclaredMethod 
     * @param Class<?> clazz : 子类 
     * @param methodName : 父类中的方法名 
     * @param parameterTypes : 父类中的方法参数类型 
     * @return 父类中的方法对象 
     */  
      
    public static Method getSuperDeclaredMethod(Class<?> clazz, String methodName, Class<?> ... parameterTypes){  
        Method method = null ;  
          
        for(; clazz != Object.class ; clazz = clazz.getSuperclass()) {  
            try {  
                method = clazz.getDeclaredMethod(methodName, parameterTypes) ;  
                return method ;  
            } catch (Exception e) {  
                //这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。  
                //如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了  
              
            }  
        }  
          
        return null;  
    } 
}
