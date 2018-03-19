package com.framework.view;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface View{
	public void execute(String result, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
