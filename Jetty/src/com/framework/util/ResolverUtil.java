
package com.framework.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.QName;
import org.dom4j.dom.DOMAttribute;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.framework.chain.Chain;
import com.framework.chain.Command;
import com.framework.config.ActionImpl;
import com.framework.style.Convertor;
import com.framework.style.Style;

public class ResolverUtil implements ApplicationContextAware{
	protected static Log log = LogFactory.getLog(ResolverUtil.class.getClass()) ;
	public static String actionXmlPath;// = "/actions.xml";
	public static String chainXmlPath ;//= "/chain.xml";
	public static String styleXmlPath ;//= "/style.xml";
	public static Map<String, ActionImpl> actionMap ;
	public static Map<String, Chain> chainMap ;
	public static Map<String, Style> styleMap ;
	private static ApplicationContext applicationContext;
	public static void init(){
		styleMap = getStyleMap();
		chainMap = getChainMap();
		actionMap = getActionMap();
	}
	static ResolverUtil resolverUtil =null;
	private ResolverUtil(){}
	public synchronized static ResolverUtil getInstance(){
		if(null==resolverUtil) resolverUtil = new ResolverUtil();
		return resolverUtil;
	}
	/**
	 * 加载Action配置
	 */
	public static Map<String, ActionImpl> getActionMap() {
		return getActionObjects(actionXmlPath, ActionImpl.class);
	}
	/**
	 * 根据actionName,得到相应的Action类
	 */
	public static ActionImpl getActionClass(String actionName) {
		return actionMap.get(actionName);
	}
	/**
	 * 加载Chain配置
	 */
	public static Map<String, Chain> getChainMap() {
		return getTObjects(chainXmlPath,Chain.class);
	}
	/**
	 * 根据chainName,得到相应的Chain类
	 */
	public static Chain getChain(String chainName) {
		return chainMap.get(chainName);
	}
	/**
	 * 加载Style配置
	 */
	public static Map<String, Style> getStyleMap() {
		return getTObjects(styleXmlPath,Style.class);
	}
	/**
	 * 根据styleName,得到相应的Style类
	 */
	public static Style getStyle(String styleName) {
		return styleMap.get(styleName);
	}
	/**
	 * 解析Action.XML - 获取Action整个配置
	 */
	public static <T> Map<String, T> getActionObjects(String filePath, Class<T> clazz) {
		try {
			InputStream is = ResolverUtil.class.getResourceAsStream(filePath);
			Document doc = XmlUtil.getDocument(is);
			Element root = XmlUtil.getRoot(doc);
			// 得到所有的package结点
			List<Element> packages = XmlUtil.getElementsByName(root, "package");
			if (packages != null && packages.size() > 0) {
				for (Element _package : packages) {
					// 得到某个package下的所有结点
					List<Element> actions = XmlUtil.getElements(_package);
					Map<String, T> actionMap = new HashMap<String, T>();
					for (Element elem : actions) {
						// 判断某个<result>结点元素的name属性是否与传递过来的actionName相等,如果相等那么将其method属性取出
						Attribute att = null!= XmlUtil.getAttributeByName(elem, "id")?XmlUtil.getAttributeByName(elem, "id"):XmlUtil.getAttributeByName(elem, "name");
						T classObject = applicationContext.getBean(clazz);//clazz.newInstance();
						ActionImpl action = (ActionImpl) classObject;
						Attribute method = XmlUtil.getAttributeByName(elem, "method");
						Attribute chain = XmlUtil.getAttributeByName(elem, "chain");
						List<Element> channelsE = XmlUtil.getElementsByName(elem, "channels");
						List<Element> fieldsE = XmlUtil.getElementsByName(elem, "fields");
						if (null!=fieldsE&&fieldsE.size()>0) {
							List<Element> fieldsEE = XmlUtil.getElementsByName(fieldsE.get(0), "field");
							LinkedHashMap<String, String> fields = new LinkedHashMap<String, String>();
							for (Element fld : fieldsEE) {
								Attribute nameAttribute = XmlUtil.getAttributeByName(fld, "name");
								fields.put(nameAttribute.getText(), fld.getText());
							}
							action.setFields(fields);
						}
						List<Element> channelsEE = XmlUtil.getElementsByName(channelsE.get(0), "channel");
						HashMap<String, Map<String, String>> channels = new HashMap<String, Map<String, String>>();
						for (Element chl : channelsEE) {
							Attribute typeAttribute = XmlUtil.getAttributeByName(chl, "type");
							Map<String, String> channel = new HashMap<String, String>();
							List<Element> results = XmlUtil.getElementsByName(chl, "result");
							for (Element result : results) {
								Attribute resultAttribute = XmlUtil.getAttributeByName(result, "name");
								channel.put(resultAttribute.getText(), result.getText());
							}
							channels.put(typeAttribute.getText(), channel);
						}
						if (null!=method)action.setMethodName(method.getText());
						action.setClassBeanId(att.getText());
						action.setChain(getChain(chain.getText()));
						action.setChannels(channels);
						actionMap.put(att.getText(), classObject);
					}
					log.debug("actionMap >> "+actionMap);
					return actionMap;
				}
			}
		} 
//		catch (InstantiationException e) {
//			e.printStackTrace();
//			throw new RuntimeException("newInstance生成对象失败");
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//			throw new RuntimeException("newInstance生成对象失败");
//		} 
		catch (DocumentException e) {
			e.printStackTrace();
			throw new RuntimeException(filePath + "不存在或有误");
		}
		throw new RuntimeException(filePath + "解析出错");
	}
	/**
	 * 解析XML - 获取整个配置
	 */
	public static <T> Map<String, T> getTObjects(String filePath, Class<T> clazz){
		try {
			InputStream is = ResolverUtil.class.getResourceAsStream(filePath);
			Document doc = XmlUtil.getDocument(is);
			Element root = XmlUtil.getRoot(doc);
			// 得到所有的package结点
			List<Element> packages = XmlUtil.getElementsByName(root, "package");
			if (packages != null && packages.size() > 0) {
				for (Element _package : packages) {
					// 得到某个package下的所有结点
					List<Element> actions = XmlUtil.getElements(_package);
					Map<String, T> actionMap = new HashMap<String, T>();
					for (Element elem : actions) {
						// 判断某个<result>结点元素的name属性是否与传递过来的actionName相等,如果相等那么将其method属性取出
						Attribute att = XmlUtil.getAttributeByName(elem, "name");
						Attribute cls = XmlUtil.getAttributeByName(elem, "class");
						if("chain".equals(elem.getQName().getName())){
							cls =new DOMAttribute(new QName("class"),"com.framework.chain.ChainImpl");
						}else if ("style".equals(elem.getQName().getName())) {
							cls =new DOMAttribute(new QName("class"),"com.framework.style.StyleImpl");
						}
						T classObject;
//						if(clazz.isInterface()){
//							Class clas = Class.forName(cls.getText());
//							classObject = (T) clas.newInstance();
//						}else {
//							classObject = clazz.newInstance();
//						}
						classObject = applicationContext.getBean(clazz);
						if (classObject instanceof Chain) {
							Chain chain = (Chain) classObject;
							LinkedHashMap<String,Command> commands = new LinkedHashMap<String,Command>();
							List<Element> commandsEle = XmlUtil.getElements(elem);
							List<Element> commondName = XmlUtil.getElements(commandsEle.get(0));
							for (Element commondEle : commondName) {
								Command e = (Command) applicationContext.getBean(commondEle.getText());
								commands.put(commondEle.getText(), e);
							}
							chain.setCommands(commands);
						}else if (classObject instanceof Style){
							Style style = (Style) classObject;
							HashMap<String,String> settings = new HashMap<String,String>();
							List<Element> settingEle = XmlUtil.getElements(elem);
							List<Element> settingParam = XmlUtil.getElements(settingEle.get(0));
							for (Element param : settingParam) {
								Attribute paramName = XmlUtil.getAttributeByName(param, "name");
								settings.put(paramName.getText(), param.getText());
								if ("convertor".equals(paramName.getText())) {
									Convertor c = (Convertor) applicationContext.getBean(param.getText());
									style.setConvertor(c);
								}
							}
							style.setSettings(settings);
						}
						actionMap.put(att.getText(), classObject);
					}
					log.debug("xmlMap >> "+actionMap);
					return actionMap;
				}
			}
		}
//		catch (ClassNotFoundException e) {
//			e.printStackTrace();
//			throw new RuntimeException("Class未找到");
//		}catch (InstantiationException e) {
//			e.printStackTrace();
//			throw new RuntimeException("newInstance生成对象失败");
//		}catch (IllegalAccessException e) {
//			e.printStackTrace();
//			throw new RuntimeException("newInstance生成对象失败");
//		}
		catch (DocumentException e) {
			e.printStackTrace();
			throw new RuntimeException(filePath + "不存在或有误");
		}
		throw new RuntimeException(filePath + "解析出错");
	}
	
	/**
	 * 根据actionName,methodName 直接得到相应的Action类
	 */
	public static ActionImpl getActionClass(String actionName,String methodName) {
		return actionMap.get(actionName);
	}
	/**
	 * 解析Action.XML--获取单个Action对象
	 */
	public static <T> T getActionObject(String filePath, Class<T> clazz, String name, Object... args){
		try {
			InputStream is = ResolverUtil.class.getResourceAsStream(filePath);
			Document doc = XmlUtil.getDocument(is);
			Element root = XmlUtil.getRoot(doc);
			// 得到所有的package结点
			List<Element> packages = XmlUtil.getElementsByName(root, "package");
			if (packages != null && packages.size() > 0) {
				for (Element _package : packages) {
					// 得到某个package下的所有结点
					List<Element> actions = XmlUtil.getElements(_package);
					for (Element elem : actions) {
						// 判断某个<result>结点元素的name属性是否与传递过来的actionName相等,如果相等那么将其method属性取出
						Attribute att = XmlUtil.getAttributeByName(elem, "name");
						Attribute cls = XmlUtil.getAttributeByName(elem, "class");
						if (StringUtil.equals(att.getText(), name)) {
							T classObject = clazz.newInstance();
							ActionImpl action = (ActionImpl) classObject;
							Attribute method = XmlUtil.getAttributeByName(elem, "method");
							Attribute chain = XmlUtil.getAttributeByName(elem, "chain");
							List<Element> channelsE = XmlUtil.getElementsByName(elem, "channels");
							List<Element> fieldsE = XmlUtil.getElementsByName(elem, "fields");
							List<Element> fieldsEE = XmlUtil.getElementsByName(fieldsE.get(0), "field");
							LinkedHashMap<String, String> fields = new LinkedHashMap<String, String>();
							for (Element fld : fieldsEE) {
								Attribute nameAttribute = XmlUtil.getAttributeByName(fld, "name");
								fields.put(nameAttribute.getText(), fld.getText());
							}
							List<Element> channelsEE = XmlUtil.getElementsByName(channelsE.get(0), "channel");
							HashMap<String, Map<String, String>> channels = new HashMap<String, Map<String,String>>();
							for (Element chl : channelsEE) {
								Attribute typeAttribute = XmlUtil.getAttributeByName(chl, "type");
								Map<String, String> channel = new HashMap<String, String>();
								List<Element> results = XmlUtil.getElementsByName(chl, "result");
								for (Element result : results) {
									Attribute resultAttribute = XmlUtil.getAttributeByName(result, "name");
									channel.put(resultAttribute.getText(), result.getText());
								}
								channels.put(typeAttribute.getText(), channel);
							}
							action.setClassBeanId(cls.getText());
							if (null != method) {
								action.setMethodName(method.getText());
							}else{
								action.setMethodName(args[0].toString());
							}
							action.setChain(getChain(chain.getText()));
							action.setChannels(channels);
							action.setFields(fields);
							return classObject;
						}
					}
				}
			}
		}catch (InstantiationException e) {
			e.printStackTrace();
			throw new RuntimeException("newInstance生成对象失败");
		}catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new RuntimeException("newInstance生成对象失败");
		} catch (DocumentException e) {
			e.printStackTrace();
			throw new RuntimeException(filePath + "不存在或有误");
		}
		throw new RuntimeException("找不到名称为 [" + name + "] 的映射");
	}
	
	/**
	 * 解析XML--获取单个对象
	 */
	public static <T> T getTObject(String filePath, Class<T> clazz, String name, Object... args){
		try {
			InputStream is = ResolverUtil.class.getResourceAsStream(filePath);
			Document doc = XmlUtil.getDocument(is);
			Element root = XmlUtil.getRoot(doc);
			// 得到所有的package结点
			List<Element> packages = XmlUtil.getElementsByName(root, "package");
			if (packages != null && packages.size() > 0) {
				for (Element _package : packages) {
					// 得到某个package下的所有结点
					List<Element> actions = XmlUtil.getElements(_package);
					for (Element elem : actions) {
						// 判断某个<result>结点元素的name属性是否与传递过来的actionName相等,如果相等那么将其method属性取出
						Attribute att = XmlUtil.getAttributeByName(elem, "name");
						Attribute cls = XmlUtil.getAttributeByName(elem, "class");
						if("chain".equals(elem.getQName().getName())){
							cls =new DOMAttribute(new QName("class"),"com.framework.chain.ChainImpl");
						}else if ("style".equals(elem.getQName().getName())) {
							cls =new DOMAttribute(new QName("class"),"com.framework.style.StyleImpl");
						}
						if (StringUtil.equals(att.getText(), name)) {
							T classObject;
							if(clazz.isInterface()){
								Class clas = Class.forName(cls.getText());
								classObject = (T) clas.newInstance();
							}else {
								classObject = clazz.newInstance();
							}
							if (classObject instanceof Chain) {
								Chain chain = (Chain) classObject;
								LinkedHashMap<String,Command> commands = new LinkedHashMap<String,Command>();
								List<Element> commandsEle = XmlUtil.getElements(elem);
								List<Element> commondName = XmlUtil.getElements(commandsEle.get(0));
								for (Element commondEle : commondName) {
									Command e= getTObject(chainXmlPath,Command.class,commondEle.getText());
									commands.put(commondEle.getText(), e);
								}
								chain.setCommands(commands);
							}else if (classObject instanceof Style){
								Style style = (Style) classObject;
								HashMap<String,String> settings = new HashMap<String,String>();
								List<Element> settingEle = XmlUtil.getElements(elem);
								List<Element> settingParam = XmlUtil.getElements(settingEle.get(0));
								for (Element param : settingParam) {
									Attribute paramName = XmlUtil.getAttributeByName(param, "name");
									settings.put(paramName.getText(), param.getText());
									if ("convertor".equals(paramName.getText())) {
										Convertor c= getTObject(styleXmlPath,Convertor.class,param.getText());
										style.setConvertor(c);
									}
								}
								style.setSettings(settings);
								
							}
							return classObject;
						}
					}
				}
			}
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Class未找到");
		}catch (InstantiationException e) {
			e.printStackTrace();
			throw new RuntimeException("newInstance生成对象失败");
		}catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new RuntimeException("newInstance生成对象失败");
		} catch (DocumentException e) {
			e.printStackTrace();
			throw new RuntimeException(filePath + "不存在或有误");
		}
		throw new RuntimeException("找不到名称为 [" + name + "] 的映射");
	}
	@SuppressWarnings("static-access")
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	public void setActionXmlPath(String actionXmlPath) {
		this.actionXmlPath = actionXmlPath;
	}
	public void setChainXmlPath(String chainXmlPath) {
		this.chainXmlPath = chainXmlPath;
	}
	public void setStyleXmlPath(String styleXmlPath) {
		this.styleXmlPath = styleXmlPath;
	}
	
}
