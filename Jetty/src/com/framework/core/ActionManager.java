package com.framework.core;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.framework.config.ActionImpl;
import com.framework.context.Context;
import com.framework.context.ContextImpl;
public class ActionManager implements ApplicationContextAware{
	private static ApplicationContext applicationContext;
	/**
	 * 调用Action,并执行Action的无参方法
	 * 
	 * @param actionClass
	 * @param request.getParameterMap()
	 * @return
	 */
	public Object getAction(ActionImpl actionClass) {
		try {
			Object obj = applicationContext.getBean(actionClass.getClassBeanId());//clas.newInstance();
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("获取Action出错");
		}
	}

	public Context setMethodParam(Map<String, String[]> params) {
		Context context = new ContextImpl();
		if (params != null && params.size() > 0) {
			Iterator<String> it = params.keySet().iterator();
			while (it.hasNext()) {
				String key = it.next();
				String[] value = params.get(key);
				if (value.length == 1) {
					context.setData(key, value[0]);
				} else {
					context.setData(key, value);
				}
				// String result = StringUtil.StringArrayToString(value);
				// context.setData(key, result);
			}

		}
		return context;
	}

	/**
	 * 调用Action,并执行Action的无参方法
	 * 
	 * @param actionClass
	 * @param obj
	 *            要处理的对象
	 * @return
	 */
	public Object invokeAction(ActionImpl actionClass,Context context, String methodName) {
		try {
			Object obj = actionClass.getAction();
			Class clas = obj.getClass();
			Method method = clas.getMethod(methodName, Context.class);
			method.invoke(obj,context);
			this.setInvokeResult(context.getChannel(),context.getStatus(), actionClass);
			actionClass.setAction(obj);
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("反射执行Action方法出错");
		}
	}

	/**
	 * 匹配<result name="success">/index.jsp</result> Xml中的result
	 * name属性，如果匹配成功,设置返回结果"/index.jsp"
	 */
	public void setInvokeResult(String channel, String result, ActionImpl actionConfig) {
		HashMap<String, Map<String, String>> channels= actionConfig.getChannels();
		String configResult = channels.get(channel).get(result);
		actionConfig.setResult(configResult);
		if (null == configResult) {
			throw new RuntimeException("请确定在xml配置文件中渠道[" + channel + "]中是否有 [" + result + "]　的返回类型结点 ");
		}
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}
