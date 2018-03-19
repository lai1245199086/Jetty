package com.spring.config;

/**
<myns:datasource id="myDataSourcce" 
        driverClassName="com.mysql.jdbc.Driver"
        url="jdbc:mysql://localhost:3309/demodb" 
        username="root" 
        password="root" /> 
 *
 */
public class DataSourceInfo{
	private String id;
	private String url;
	private String username;
	private String password;
	private String driverClassName;
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDriverClassName() {
		return driverClassName;
	}
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}
	
}
