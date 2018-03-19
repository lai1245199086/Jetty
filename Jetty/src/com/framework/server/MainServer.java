package com.framework.server;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jetty.server.Connector;  
import org.eclipse.jetty.server.Server;  
import org.eclipse.jetty.server.nio.SelectChannelConnector;  
import org.eclipse.jetty.webapp.WebAppContext;  

import com.spring.config.DataSourceInfo;
import com.spring.config.SpringContextUtil;
  
public class MainServer {
	protected static Log log = LogFactory.getLog(MainServer.class) ;
    public static void main(String[] args) throws Exception {  
        Server server = new Server();  
  
        Connector connector = new SelectChannelConnector();  
        connector.setPort(8080);//端口号  
  
        server.setConnectors(new Connector[] { connector });  
  
        WebAppContext webAppContext = new WebAppContext("WebContent","/Jetty");  
  
        //webAppContext.setContextPath("/");  
        webAppContext.setDescriptor("WebContent/WEB-INF/web.xml");  
        webAppContext.setResourceBase("WebContent");  
        webAppContext.setDisplayName("Jetty");  
        //定位action的class文件位置
        webAppContext.setClassLoader(Thread.currentThread().getContextClassLoader());  
        webAppContext.setConfigurationDiscovered(true);  
        webAppContext.setParentLoaderPriority(true);  
        //设置项目访问名称
//        webAppContext.setContextPath("Jetty");
        server.setHandler(webAppContext);  
        log.info("访问根路径 >> "+webAppContext.getContextPath());  
        log.info("web.xml路径 >> "+webAppContext.getDescriptor());  
        log.info("访问资源的根路径 >> "+webAppContext.getResourceBase());  
  
        try {  
            server.start();  
        } catch (Exception e) {  
        	log.error("Server started failed !");  
            e.printStackTrace();  
        }  
        System.out.println("Server has already started");  
        log.info("Server has already started");  
        //访问http://localhost:8080/Jetty/ 得到index.jsp页面内容
        
    }  
}  
