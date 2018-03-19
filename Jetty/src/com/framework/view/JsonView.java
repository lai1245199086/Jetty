package com.framework.view;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

public class JsonView implements View{
	protected Log log = LogFactory.getLog(this.getClass()) ;
	@Override
	public void execute(String result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Enumeration<String> requesEnum = request.getAttributeNames();
		Map<String, Object> params = new HashMap<String, Object>();
		while(requesEnum.hasMoreElements()){
			String key = requesEnum.nextElement();
			params.put(key, request.getAttribute(key));
		}
		ObjectMapper mapper = new ObjectMapper(); 
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");
		response.setHeader("pragma", "no-cache");
		response.setHeader("cache-control", "no-cache");
		//PrintWriter writer = response.getWriter();
		//mapper.writeValue(writer,params);
		//writer.flush();
		//writer.close();
		log.info("Result JSON >> "+params);
		mapper.writeValue(response.getOutputStream(),params);
	}
	
	/*
	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("key1", 21321);
		map.put("string", "cll");
		ObjectMapper mapper = new ObjectMapper(); 
		mapper.writeValue(System.out,map);
	}
	*/
}
