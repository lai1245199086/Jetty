package com.framework.server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author jake1036 该类为助手类，用于管理连接池
 */
public class PoolConnectionHandler implements Runnable {
	Log log = LogFactory.getLog(getClass());
	protected Socket connection;
	protected static List<Socket> pool = new LinkedList<Socket>();

	/**
	 * @param requestToHandler
	 * 每接收一个请求就在池中增加一个socket 并通知所有等待的进程
	 */
	public static void processRequest(Socket requestToHandler) {
		synchronized (pool) {
			pool.add(pool.size(), requestToHandler);
			pool.notifyAll();
		}

	}

	public void handleConnection() {
		log.debug(Thread.currentThread().getName() + " handler " + connection);
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
			writer.write(Thread.currentThread().getName() + " handle me " + connection.getPort());
			log.debug("Retrun message >> "+Thread.currentThread().getName() + " handle " + connection.getPort());
			writer.flush();
			writer.close();
		} catch (IOException e) {
			log.error(e);
		}
	}

	public void run() {

		/**//*
			 * 此处while true循环是必须的 否则该对象实例将会消失，达不到连接池的效果
			 */
		while (true) {
			synchronized (pool) {
				while (pool.isEmpty()) {
					try {
						pool.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
						return;
					}
				}
				connection = pool.remove(0);
			}

			handleConnection();

		}
	}
}
