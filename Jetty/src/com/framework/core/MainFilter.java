/************************************************************

  Copyright (C), 1996-2008, resoft Tech. Co., Ltd.
  FileName: StrutsFilter.java
  Author: superleo       
  Version: 1.0     
  Date:2008-8-5 上午11:23:51
  Description: StrutsFilter   

  Function List:   
    1. -------

  History:         
      <author>    <time>   <version >   <desc>


 ***********************************************************/

package com.framework.core;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.framework.chain.Command;
import com.framework.chain.DelegateCommand;
import com.framework.config.ActionImpl;
import com.framework.context.Context;
import com.framework.handler.ExceptionHandler;
import com.framework.util.ResolverUtil;
import com.framework.util.StringUtil;
import com.framework.view.View;
import com.framework.view.ViewImpl;

/**
 * @author superleo
 * @since 2008-8-5
 * @version
 */
public class MainFilter implements Filter,ApplicationContextAware {
	protected Log log = LogFactory.getLog(this.getClass()) ;
	private FilterConfig filterConfig;
	private ActionManager  actionManager;
	private CommonsMultipartResolver  multipartResolver;
	private ApplicationContext applicationContext;
	private View view;
	private ExceptionHandler exceptionHandler;

	public void init(FilterConfig arg0) throws ServletException {
		this.filterConfig = arg0;
		ResolverUtil.init();//加载xml配置
//		actionManager = (ActionManager) applicationContext.getBean("ActionManager");
//		multipartResolver = (CommonsMultipartResolver) applicationContext.getBean("MultipartResolver");
//		view = (ViewImpl) applicationContext.getBean("ViewImpl");
//		exceptionHandler = (ExceptionHandler) applicationContext.getBean("ExceptionHandler");
		
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		ServletContext servletContext = filterConfig.getServletContext();
		//获取Header中的参数
		String channel = request.getHeader("_Channel");
		Locale locale = null!=request.getLocale()?request.getLocale(): Locale.getDefault();//语言
		log.info("访问的Local >>"+locale);
		String protocol = request.getProtocol();//访问协议
		String scheme = request.getScheme();//协议头
		String remoteAddr = request.getRemoteAddr();//访问IP
		int port = request.getRemotePort();//客户浏览器使用该端口访问
		String remoteUser = request.getRemoteUser();
		String requestedSessionId = request.getRequestedSessionId();
		String requestURI = request.getRequestURI();//访问的URI
		String accept = request.getHeader("accept");
		String referer = request.getHeader("referer");
		String userAgent = request.getHeader("user-agent");
		//getOS(userAgent);//客户使用的操作系统
		//getNavigator(userAgent);// 客户使用的浏览器
		
		//服务器信息
		String serverInfo = servletContext.getServerInfo();
		String localAddr = request.getLocalAddr();
		String localName = request.getLocalName();
		
		// 解析Request的URL和传过来的参数
		String actionName = StringUtil.parseServletPath(request.getServletPath());
		//log.info("调用的ActionName >> "+actionName);
		String methodName = StringUtil.getMethodName(request.getServletPath());
		methodName = null==methodName?"execute":methodName;
		//log.info("调用的ActionMethod >> "+methodName);
		
		//文件上传
		multipartResolver.setServletContext(servletContext);
		uploadFile(request);
		
		// 如果后缀不为.action,那么直接放过，不进行拦截
		if (StringUtil.isEmpty(actionName)) {
			chain.doFilter(request, response);
		} else {
			// 解析得到ActionClass,里面包括Action的类全名,返回页面值,Action执行的方法
			ActionImpl clas = this.getActionClass(actionName,methodName);
			// 得到页面的所有parameters参数(没考虑上传情况)
			Map<String, String[]> params = request.getParameterMap();
			log.info(request.getInputStream());
			// 为要调用的Action的set**方法设值,并返回要调用的Action对象本身
			Context context = setBeforeActionValue(clas,actionName ,methodName, params);
			context.setSesstion(request.getSession());
			context.setRequest(request);
			if (null!=channel) {
				context.setChannel(channel);
			}
			context.setLocale(locale);
			context.setData("fileName", request.getAttribute("fileName"));
			try {
				// 调用的Action执行方法,并返回值设置在request域中
				executAndSetResultValue(clas,context,methodName, request);
			} catch (Exception e) {
				//定义异常类，集中处理请求中的异常
				if (null!= e.getCause()) {
					e = (Exception)e.getCause();
				}
				log.error(e.getMessage());
				clas.setResult(",json");
				request.setAttribute("AjaxCode", "9999");
				request.setAttribute("AjaxError", exceptionHandler.parseLocalizedMessage(context,e));
				// 返回相应的JSP页面
				String result = clas.getResult();
				log.info("返回结果 >>"+result);
				resolveView(result,request,response);
				//抛出异常
				exceptionHandler.execute(context,e);
			}
			// 返回相应的JSP页面
			String result = clas.getResult();
			log.info("返回结果 >>"+result);
			resolveView(result,request,response);
			
			
		}
	}
	
	/**
	 * 解析返回结果页
	 */
	private void resolveView(String result, HttpServletRequest request, HttpServletResponse response){
		try {
			view.execute(result, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("解析返回结果出错");
		}
	}

	private void uploadFile(HttpServletRequest request) throws IOException {
		//判断 request 是否有文件上传,即多部分请求...  
		if (multipartResolver.isMultipart(request)) {
			// 转换成多部分request
			MultipartHttpServletRequest multipartRequest = multipartResolver.resolveMultipart(request);
			// file 是指 文件上传标签的 name=值
			// 根据 name 获取上传的文件...
			MultipartFile sourceFile = multipartRequest.getFile("file");
			String fileName = sourceFile.getOriginalFilename();
			request.setAttribute("fileName", fileName);
			// 上传后记录的文件...
			File targetFile = new File(System.getProperty("webapp.root")+"\\upload\\"+fileName);
			// 上传...
			sourceFile.transferTo(targetFile);
			//FileCopyUtils.copy(sourceFile.getBytes(),targetFile);
		}
	}

	/**
	 * 提到ActionClass,里面包括要处理的类,返回页面值,等详细信息
	 * @param actionName
	 * @see com.framework.core.ActionImpl
	 * @return
	 */
	private ActionImpl getActionClass(String actionName,String methodName) {
		if (StringUtil.isNotBlank(actionName)) {
			// 判断是否存在此actionName
			return ResolverUtil.getActionClass(actionName);
		}
		throw new RuntimeException("actionName 不能为空");
	}

	/**
	 * 在Action处理之前,将页面的参数与Action对应的set**方法进行调用。
	 * @param clas  ActionClass
	 * @param params  request.getParameterMap()
	 * @return Action对象
	 * 
	 */
	private Context setBeforeActionValue(ActionImpl clas,String actionName,String methodName, Map<String, String[]> params) {
		Object action = actionManager.getAction(clas);
		Context context = actionManager.setMethodParam(params);
		String transactionId = actionName+"!"+methodName;
		clas.setMethodName(methodName);
		clas.setAction(action);
		context.setActionConfig(clas);
		context.setTransactionId(transactionId);
		log.debug("调用的TransactionId >> " + transactionId);
		return context;
	}

	/**
	 * 调用Action的执行方法,并将结果存于request域中
	 * @param clas ActionClass类
	 * @param request
	 */
	private void executAndSetResultValue(ActionImpl clas,Context context,String methodName, HttpServletRequest request) throws Exception{
		//Object obj = manager.invokeAction(clas,context,methodName);
		DelegateCommand delegateCommand = (DelegateCommand)clas.getChain().getCommands().get("DelegateCommand");
		delegateCommand.setC(clas.getAction().getClass());
		delegateCommand.setMethod(methodName);
		delegateCommand.setParamsType(Context.class);
		delegateCommand.setParams(context);
		clas.getChain().execute(context);
		actionManager.setInvokeResult(context.getChannel(),context.getStatus(), clas);
		
		Map<String, Object> dataMap = context.getDataMap();
		for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
			request.setAttribute(entry.getKey(), entry.getValue());
		}
	}

	public void destroy() {
	}
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public void setActionManager(ActionManager actionManager) {
		this.actionManager = actionManager;
	}

	public void setMultipartResolver(CommonsMultipartResolver multipartResolver) {
		this.multipartResolver = multipartResolver;
	}

	public void setView(View view) {
		this.view = view;
	}

	public void setExceptionHandler(ExceptionHandler exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
	}
	
}
