package com.spring.config;

/**
 * <aty:datasource 
 * id="myDataSourcce" 
 * url="jdbc:mysql://localhost:3309/demodb" 
 * userName="root" 
 * password="root" />  
 *
 */
public class DataSourceInfo{
	private String id;
	private String url;
	private String userName;
	private String password;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
