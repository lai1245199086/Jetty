package com.framework.service;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

public class XmlParserImpl extends XmlParser{
	protected Log log = LogFactory.getLog(getClass());
	private String startTag;
	
	@Override
	public Object parserXmlMsg(Object object,Object head) {
		OutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream("src/com/config/outbound/demo.xml");
			outputStream = new ByteArrayOutputStream();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Map paramMap = (Map)object;
		HeadTemplate headTemplate = (HeadTemplate)head;
		//1.创建XMLInputFactory
		XMLInputFactory factoryIn = XMLInputFactory.newInstance();  
		factoryIn.setProperty(XMLInputFactory.IS_COALESCING, Boolean.TRUE);   
		//2.通过XMLInputFactory 创建StAXReader
		XMLStreamReader headReader = null;
		
		//创建输出流对象工厂
		XMLOutputFactory factoryOut = XMLOutputFactory.newInstance();
		//创建输出流对象
		XMLStreamWriter streamWriter =null;
		
		//3.使用 StAX 解析 XML
		try {
			Long startTime = System.currentTimeMillis();  
			headReader = factoryIn.createXMLStreamReader(headTemplate.getFileReader());
			streamWriter = factoryOut.createXMLStreamWriter(outputStream);
			xmlParserSupport(headReader,streamWriter,paramMap,factoryIn);
			long endTime = System.currentTimeMillis();
	        log.debug("耗时: " + (endTime-startTime) + "毫秒"); 
		} catch (XMLStreamException e) {
			e.printStackTrace();
			throw new RuntimeException("XMLStream cause exception");
		}finally {  
            try {
            	headReader.close();
			} catch (XMLStreamException e) {
				throw new RuntimeException("XMLStream close cause exception");
			}  
        }
		
		return outputStream;
	}

	private void xmlParserSupport(XMLStreamReader headReader,XMLStreamWriter streamWriter, Map paramMap,XMLInputFactory factoryIn) throws XMLStreamException {
		int event = headReader.getEventType(); //获取节点类型,结果是以整形的方式返回
		while(headReader.hasNext()) {
			switch (event) {
		    case XMLStreamConstants.START_DOCUMENT://表示的是文档的开通节点。  
		    	//创建xml文档，根据对象方法创建对象元素
				streamWriter.writeStartDocument(headReader.getCharacterEncodingScheme(),headReader.getVersion());
				event = headReader.next();
				break;  
		    case XMLStreamConstants.START_ELEMENT://开始解析开始节点  
		    	int attributeCount= headReader.getAttributeCount();
		    	QName xmlTag = headReader.getName();
		    	if(attributeCount > 0) {
		    		QName xmlAttribute = headReader.getAttributeName(0);
		    		String attributeValue = headReader.getAttributeValue(xmlAttribute.getNamespaceURI(), xmlAttribute.getLocalPart());
		    		
		    		if ("tagName".equals(xmlAttribute.getLocalPart())) {
		    			streamWriter.writeStartElement(attributeValue);
		    			event = headReader.next();
		    			xmlParserSupport(headReader,streamWriter,paramMap,factoryIn);
					}else if("keyName".equals(xmlAttribute.getLocalPart())){
						QName xmlAttribute0 = headReader.getAttributeName(0);
						String attributeValue0 = headReader.getAttributeValue(xmlAttribute0.getNamespaceURI(), xmlAttribute0.getLocalPart());
						Object trsCode = paramMap.get(attributeValue0);
						
						QName xmlAttribute1 = headReader.getAttributeName(1);
						String attributeValue1 = headReader.getAttributeValue(xmlAttribute1.getNamespaceURI(), xmlAttribute1.getLocalPart());
						
						event = headReader.next();
						BodyTemplate body = new BodyTemplate((String)trsCode,attributeValue1);
						BodyTemplate bodyTtemplate = (BodyTemplate)body;
						XMLStreamReader bodyReader = factoryIn.createXMLStreamReader(bodyTtemplate.getFileReader());
						xmlParserBody(bodyReader,streamWriter, paramMap);
					}else{
						String characters = "";
						Object paramObj =  null;
						SimpleDateFormat format = null;
						QName xmlAttribute0 = headReader.getAttributeName(0);
						String attributeValue0 = headReader.getAttributeValue(xmlAttribute0.getNamespaceURI(), xmlAttribute0.getLocalPart());
						if ("name".equals(xmlAttribute0.getLocalPart())) {
							paramObj =  paramMap.get(attributeValue0);
						}
						if ("String".equals(xmlTag.getLocalPart())) {
							if (StringUtils.isEmpty(paramObj)) {
								QName xmlAttribute1 = headReader.getAttributeName(1);
								String attributeValue1 = headReader.getAttributeValue(xmlAttribute1.getNamespaceURI(), xmlAttribute1.getLocalPart());
								paramObj = attributeValue1;
							}
							characters = (String)paramObj;
						}else if("Date".equals(xmlTag.getLocalPart())) {
							QName xmlAttribute1 = headReader.getAttributeName(1);
							String attributeValue1 = headReader.getAttributeValue(xmlAttribute1.getNamespaceURI(), xmlAttribute1.getLocalPart());
							if("pattern".equals(xmlAttribute1.getLocalPart())){
								format = new SimpleDateFormat(attributeValue1);
							}
							characters = format.format(paramObj);
						}
						streamWriter.writeCharacters(characters);
						event = headReader.next();
						xmlParserSupport(headReader,streamWriter,paramMap,factoryIn);
					}
				}
				 break; 
		    case XMLStreamConstants.END_ELEMENT:  //文档的结束元素 
		    	QName xmlTag1 = headReader.getName();
		    	String xmlTagName = xmlTag1.getLocalPart();
		    	if (!"String".equals(xmlTagName)&&!"Date".equals(xmlTagName)&&!"include".equals(xmlTagName)) {
		    		streamWriter.writeEndElement();
				}
		    	if (headReader.hasNext())
		    	event = headReader.next();
		        break;
		     /*case XMLStreamConstants.CHARACTERS:  
		            // if(reader.isWhiteSpace())  
		            // break;  
		            break;*/
		     case XMLStreamConstants.END_DOCUMENT:  //文档的结束
		         streamWriter.writeEndDocument();
		         break;  
		     default:
		    	 event = headReader.next();
		     } 
			
		}
	}
	
	private void xmlParserBody(XMLStreamReader bodyReader,XMLStreamWriter streamWriter, Map paramMap) throws XMLStreamException {
		int event = bodyReader.getEventType(); //获取节点类型,结果是以整形的方式返回
		while(bodyReader.hasNext()) {
			switch (event) {
		    case XMLStreamConstants.START_ELEMENT://开始解析开始节点  
		    	int attributeCount= bodyReader.getAttributeCount();
		    	QName xmlTag = bodyReader.getName();
		    	if(attributeCount > 0) {
		    		QName xmlAttribute = bodyReader.getAttributeName(0);
		    		String attributeValue = bodyReader.getAttributeValue(xmlAttribute.getNamespaceURI(), xmlAttribute.getLocalPart());
		    		if ("tagName".equals(xmlAttribute.getLocalPart())) {
		    			streamWriter.writeStartElement(attributeValue);
		    			event = bodyReader.next();
		    			xmlParserBody(bodyReader,streamWriter,paramMap);
					}else{
						String characters = "";
						Object paramObj =  null;
						SimpleDateFormat format = null;
						QName xmlAttribute0 = bodyReader.getAttributeName(0);
						String attributeValue0 = bodyReader.getAttributeValue(xmlAttribute0.getNamespaceURI(), xmlAttribute0.getLocalPart());
						if ("name".equals(xmlAttribute0.getLocalPart())) {
							paramObj =  paramMap.get(attributeValue0);
						}
						if ("String".equals(xmlTag.getLocalPart())) {
							if (StringUtils.isEmpty(paramObj)) {
								QName xmlAttribute1 = bodyReader.getAttributeName(1);
								String attributeValue1 = bodyReader.getAttributeValue(xmlAttribute1.getNamespaceURI(), xmlAttribute1.getLocalPart());
								paramObj = attributeValue1;
							}
							characters = (String)paramObj;
						}else if("Date".equals(xmlTag.getLocalPart())) {
							QName xmlAttribute1 = bodyReader.getAttributeName(1);
							String attributeValue1 = bodyReader.getAttributeValue(xmlAttribute1.getNamespaceURI(), xmlAttribute1.getLocalPart());
							if("pattern".equals(xmlAttribute1.getLocalPart())){
								format = new SimpleDateFormat(attributeValue1);
							}
							characters = format.format(paramObj);
						}
						streamWriter.writeCharacters(characters);
						event = bodyReader.next();
						xmlParserBody(bodyReader,streamWriter,paramMap);
					}
				}
				 break;
		    case XMLStreamConstants.END_ELEMENT:  //文档的结束元素 
		    	QName xmlTag1 = bodyReader.getName();
		    	String xmlTagName = xmlTag1.getLocalPart();
		    	if (!"String".equals(xmlTagName)&&!"Date".equals(xmlTagName)&&!"include".equals(xmlTagName)) {
		    		streamWriter.writeEndElement();
				}
		    	if (bodyReader.hasNext())
		    	event = bodyReader.next();
		        break;
		    case XMLStreamConstants.END_DOCUMENT:  //文档的结束
		    	bodyReader.close();
		        break;
		     default:
		    	event = bodyReader.next();
		     } 
			
		}
	}

	public void setStartTag(String startTag) {
		this.startTag = startTag;
	}
	
}
