package com.framework.view;


import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StreamView implements View{
	protected Log log = LogFactory.getLog(this.getClass()) ;
	private static final String PNG = "image/png;charset=utf-8";  
	@Override
	public void execute(String result, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String imagePath = System.getProperty("webapp.root") + "\\assets\\images\\staff1.png";
		String fileName = request.getParameter("fileName");
		if (null!=fileName) {
			imagePath = System.getProperty("webapp.root") + "\\upload\\"+fileName;  
		}
		File file = new File(imagePath);
		// 获取输出流
        OutputStream outputStream = response.getOutputStream();
        FileInputStream fileInputStream = new FileInputStream(file);
        // 读数据
        byte[] data = new byte[fileInputStream.available()];
        fileInputStream.read(data);
        fileInputStream.close();
        // 回写
        response.setContentType(PNG);
        outputStream.write(data);
        outputStream.flush();
        outputStream.close();
	}
	
}
