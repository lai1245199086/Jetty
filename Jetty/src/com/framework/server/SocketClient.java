package com.framework.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.framework.constants.Constant;
import com.framework.util.ReadUtil;

/**
 * 连接池测试类
 * @author Administrator
 *
 */
public class SocketClient implements Runnable {
	private Log log = LogFactory.getLog(getClass());
	
	public void run() {
		try {
			Socket socket = new Socket("127.0.0.1", 60000);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),Constant.CHARSET));
			InputStream inFileStream = getClass().getClassLoader().getResourceAsStream("com/framework/server/in.xml");
			BufferedReader reader = new BufferedReader(new InputStreamReader(inFileStream));
			String line=null;
			while((line=reader.readLine())!=null){
				writer.write(line);
			}
			/*writer.write("00000234<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
					+ "<root>"
					+ "<head>"
					+ "<transcode>MD0032</transcode>"
					+ "<jnlno>100012332</jnlno>"
					+ "</head>"
					+ "<body>"
					+ "<name>李量fff</name>"
					+ "<age>25www</age>"
					+ "</body>"
					+ "</root>");*/
			writer.flush();
			
			// 读取客户端数据
			InputStream in = socket.getInputStream();
			// 由Socket对象得到输入流，并构造相应的BufferedReader对象
			byte[] headContent= new byte[Constant.HEADLENGTH];
			in.read(headContent);
			int headLength = Integer.parseInt(new String(headContent));
			log.debug("Get Client Data  Length >> "+ headLength);
			
			byte[] bodyContent= new byte[headLength];
		    in.read(bodyContent);
			// 处理客户端数据
			log.info("收到消息 >> " + new String(ReadUtil.byteMerger(headContent,bodyContent),Constant.CHARSET));
			
			in.close();
			writer.close();
			socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		for (int i = 0; i < 1; i++) {
			new Thread(new SocketClient()).start();

		}
	}

}
