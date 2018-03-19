package com.framework.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 该类通过Executor接口实现服务器
 */
public class ServerPoolTransPort {
	private static Log log = LogFactory.getLog(ServerPoolTransPort.class);
	private static int MAX_POOL_SIZE = 100;
	public static void main(String[] args) throws IOException{
        //服务端在20006端口监听客户端请求的TCP连接 
        ServerSocket server = new ServerSocket(60000);
        Socket client = null;
        //通过调用Executors类的静态方法，创建一个ExecutorService实例
        //ExecutorService接口是Executor接口的子接口
        Executor service = Executors.newFixedThreadPool(MAX_POOL_SIZE);
        log.info("服务器连接池启动成功！");
        boolean f = true;
        while(f){
            //等待客户端的连接
            client = server.accept();
            log.info(client +"与客户端连接成功！");
            //调用execute()方法时，如果必要，会创建一个新的线程来处理任务，但它首先会尝试使用已有的线程，
            //如果一个线程空闲60秒以上，则将其移除线程池；
            //另外，任务是在Executor的内部排队，而不是在网络中排队
            service.execute(new ServerHandlerThread(client));
        } 
        server.close();
    }
}
