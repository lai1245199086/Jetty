package com.framework.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.framework.token.TokenProccessor;

/*
 * 获取token
 * 
 */

public class TokenTag extends TagSupport {

	private static final long serialVersionUID = 1L;
	public int doStartTag()  
    {  
        return super.SKIP_BODY;  
    }  
	
	public int doEndTag() throws JspException  
    {  
		String token = TokenProccessor.getInstance().makeToken();//创建token
		pageContext.getSession().setAttribute("_token", token);
		JspWriter out = pageContext.getOut();
		try {
			out.println("<input type=\"hidden\" name=\"_token\" value=\""+token+"\" />");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        return super.EVAL_PAGE;  
    }  

}