package com.framework.service;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLSocket;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.SocketUtils;
import org.springframework.util.StringUtils;

import com.framework.constants.Checkmsg;
import com.framework.constants.Dict;
import com.framework.exception.CommonException;
import com.framework.exception.CommunicationException;
import com.framework.server.MainServer;

public class TransformTransport implements Transport{
	private Log log = LogFactory.getLog(getClass()) ;
	private String endTag = "root";
	private String charSet;
	private String host;
	private int port;
	private Parser parser;
	private boolean debug = false;
	
	private static ThreadLocal threadLocal = new ThreadLocal(){
		protected Object initialValue() {
			return new ArrayList();
		};
	};
	
	@Override
	public synchronized Object submit(Object object) throws CommunicationException,CommonException {
		HeadTemplate head = new HeadTemplate("OutboundPacket.xml");
		Object parserObj = parser.parserMsg(object,head);
		if (debug) {
			//写报文文件
			ByteArrayOutputStream stream = (ByteArrayOutputStream)parserObj;
			try {
				FileOutputStream fileOutputStream = new FileOutputStream("src/com/config/outbound/demo.xml");
				stream.writeTo(fileOutputStream);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Socket socket = null;
		try {
			socket = new Socket(host,port);
			configSocket(socket);
		} catch (UnknownHostException e1) {
			throw new CommonException(Checkmsg.UNKNOWN_HOST,new Object[]{host+":"+port});
		} catch (IOException e1) {
			throw new CommonException(Checkmsg.IO_EXCEPTION);
		}
		//SSLSocket
		try {
			OutputStream outputStream = socket.getOutputStream();
			log.debug("send message >> " + parserObj.toString());
			
			String newString = String.format("%0"+8+"d", parserObj.toString().getBytes("GBK").length);
			//IOUtils.write((newString+parserObj.toString()).getBytes("GBK"), outputStream);
			//读取服务器端数据    
            DataInputStream input = new DataInputStream(socket.getInputStream());    
            //向服务器端发送数据    
            DataOutputStream out = new DataOutputStream(socket.getOutputStream()); 
            out.writeUTF(parserObj.toString()); 
            String ret = input.readUTF();     
            System.out.println("服务器端返回过来的是: " + ret);
            out.close();  
            input.close();
			/*outputStream.flush();
			//IOUtils.closeQuietly(outputStream);
			
			InputStream inputStream = socket.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			Matcher matcher;
			do {
				String bufferStr = bufferedReader.readLine();
				if (null == bufferStr) {
					break;
				}
				byteArrayOutputStream.write(bufferStr.getBytes("ISO8859-1"));
				byteArrayOutputStream.write("\r\n".getBytes());
				matcher = Pattern.compile("</ *" + endTag +" *>").matcher(bufferStr);
			} while (!matcher.find());
			 byte[] resultBytes = byteArrayOutputStream.toByteArray();
			 byteArrayOutputStream.flush();
			 byteArrayOutputStream.close();
			 IOUtils.closeQuietly(inputStream);
			 log.debug("return message >> " + new String(resultBytes,"GBK"));//
*/			
		}catch (SocketTimeoutException e0) {
			log.error("通讯超时");
			e0.printStackTrace();
		}
		catch (IOException e) {
			log.error("IO异常");
			e.printStackTrace();
		}finally{
			try {
				socket.close();
			} catch (IOException e) {
				log.error("socket关闭异常");
				e.printStackTrace();
			}
		}
		
		return null;
	}
	private void configSocket(Socket socket) throws SocketException {
		socket.setTcpNoDelay(true);  //true表示关闭Socket的缓冲,立即发送数据
		socket.setReuseAddress(true); //表示是否允许重用Socket所绑定的本地地址
		socket.setSoTimeout(30000); //表示接收数据时的等待超时时间,单位毫秒 
		socket.setSoLinger(true, 5); //底层Socket延迟5秒后再关闭,而5秒后所有未发送完的剩余数据也会被丢弃
		socket.setSendBufferSize(1024);//表示发送数据的缓冲区的大小
		socket.setReceiveBufferSize(1024);//表示接收数据的缓冲区的大小  
		socket.setKeepAlive(true);  //关闭长时间处于空闲状态的Socket
		socket.setOOBInline(true); //发送一个字节的TCP紧急数据
		/**
		 //该方法用于设置服务类型,以下代码请求高可靠性和最小延迟传输服务(把0x04与0x10进行位或运算)  
            //Socket类用4个整数表示服务类型  
            //0x02:低成本(二进制的倒数第二位为1)  
            //0x04:高可靠性(二进制的倒数第三位为1)  
            //0x08:最高吞吐量(二进制的倒数第四位为1)  
            //0x10:最小延迟(二进制的倒数第五位为1) 
		 */
		socket.setTrafficClass(0x04 | 0x10); 
		/**
		 //该方法用于设定连接时间,延迟,带宽的相对重要性(该方法的三个参数表示网络传输数据的3项指标)  
            //connectionTime--该参数表示用最少时间建立连接  
            //latency---------该参数表示最小延迟  
            //bandwidth-------该参数表示最高带宽  
            //可以为这些参数赋予任意整数值,这些整数之间的相对大小就决定了相应参数的相对重要性  
            //如这里设置的就是---最高带宽最重要,其次是最小连接时间,最后是最小延迟 
		 */
		socket.setPerformancePreferences(2, 1, 3);
		
		
	}
	public void setDebug(boolean debug) {
		this.debug = debug;
	}
	public void setEndTag(String endTag) {
		this.endTag = endTag;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public void setCharSet(String charSet) {
		this.charSet = charSet;
	}
	public void setParser(Parser parser) {
		this.parser = parser;
	}
	
	public static void main(String[] args) throws CommunicationException, CommonException {
		Long startTime = System.currentTimeMillis();  
		int executeTimes = 1000;
		for (int i = 0; i < executeTimes; i++) {
			
		 Runnable thread = new Runnable() {
				public void run() {
					Map paramMap = new ConcurrentHashMap();
					paramMap.put("_TransDate", new Date());
					paramMap.put("_TransTime", new Date());
					paramMap.put("_JnlNo", String.valueOf(new Random(System.currentTimeMillis()).nextInt()));
					paramMap.put(Dict.HOSTTRANSCODE, "MD0003");
					paramMap.put("TransCode", "pcommon.queryAcNo");
					
					paramMap.put("AcNo", "6210888321300000322");
					paramMap.put("BeginDate", new Date());
					//paramMap.put("UserName", "李四");
					Transport transport = new TransformTransport();
					transport.setHost("localhost");
					transport.setPort(60000);
					transport.setCharSet("UTF-8");
					Parser parser = new XmlParserImpl();
					transport.setParser(parser);
					parser.setEndTag("");
					try {
						transport.submit(paramMap);
					} catch (CommunicationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (CommonException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}
				};
				thread.run();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("执行"+executeTimes+"次耗时: " + (endTime-startTime) + "毫秒"); 
	}
}
