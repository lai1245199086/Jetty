package com.framework.server;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.framework.constants.Constant;
import com.framework.util.ReadUtil;

/**
 * 该类为多线程类，用于服务端
 */
public class ServerHandlerThread implements Runnable {
	private Log log = LogFactory.getLog(getClass());
	private Socket client = null;

	public ServerHandlerThread(Socket client) {
		this.client = client;
	}

	@Override
	public void run() {
		execute(client);
	}

	// 处理通信细节的静态方法，这里主要是方便线程池服务器的调用
	public synchronized void execute(Socket client) {
		try {
			// 读取客户端数据
			InputStream in = client.getInputStream();
			// 由Socket对象得到输入流，并构造相应的BufferedReader对象
			byte[] headContent= new byte[Constant.HEADLENGTH];
			in.read(headContent);
			int bodyLength = Integer.parseInt(new String(headContent));
			log.debug("Get Client Data  Length >> "+ bodyLength);
			//
			byte[] bodyContent= new byte[bodyLength];
		    in.read(bodyContent);
//			byte[] bodyContent = ReadUtil.readFixedBytes(in,bodyLength,"GBK");
			// 处理客户端数据
			log.info("收到消息 >> " + new String(ReadUtil.byteMerger(headContent,bodyContent),Constant.CHARSET));

			// 向客户端回复信息
			PrintWriter write = new PrintWriter(new OutputStreamWriter(client.getOutputStream(),Constant.CHARSET));
			// 发送键盘输入的一行
			String returnStr = "00000218<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
							+ "<root>"
							+ "<head>"
							+ "<transcode>MD0032</transcode>"
							+ "<jnlno>100012332</jnlno>"
							+ "<message>交易成功</message>"
							+ "<transtime>"+System.currentTimeMillis()+"</transtime>"
							+ "</head>"
							+ "<body>"
							+ "</body>"
							+ "</root>";
			InputStream inFileStream = getClass().getClassLoader().getResourceAsStream("com/framework/server/out.xml");
			BufferedReader reader = new BufferedReader(new InputStreamReader(inFileStream));
			String line=null;
			while((line=reader.readLine())!=null){
				write.print(line);;
			}
			//log.info("返回消息 >> " + returnStr);
			//write.println(returnStr);
			write.flush();// //刷新输出流，使client马上收到该字符串
			// 将接收到的字符串前面加上echo，发送到对应的客户端
			write.close();
			in.close();
			client.close();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
	}
	
}
