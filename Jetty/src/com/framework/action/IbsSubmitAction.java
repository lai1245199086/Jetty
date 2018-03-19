package com.framework.action;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mybatis.spring.SqlSessionTemplate;

import com.framework.context.Context;
import com.framework.token.TokenProccessor;
import com.framework.constants.Checkmsg;
import com.framework.exception.ValidationException;

public abstract class IbsSubmitAction implements BaseSubmitAction{
	protected Log log = LogFactory.getLog(this.getClass()) ;
	protected SqlSessionTemplate sqlMap; 
	
	@Override
	public SqlSessionTemplate getSqlMap() {
		return sqlMap;
	}
	@Override
	public void setSqlMap(SqlSessionTemplate sqlMap) {
		this.sqlMap = sqlMap;
	}
	
	
	@Override
	public void execute(Context context) throws Exception{
		this.prepare(context);
		this.checkToken(context);
		
		this.submit(context);
	}
	
	 /**
     * 判断客户端提交上来的令牌和服务器端生成的令牌是否一致
     *  true 用户重复提交了表单 
     *  false 用户没有重复提交表单
     */
    private boolean isRepeatSubmit(Context context) {
    	Object pageToken = context.getData("_token");
        Object serverToken = context.getSession().getAttribute("_token");
        
        //1、如果用户提交的表单数据中没有token，则用户是重复提交了表单
        if(pageToken==null){
            return true;
        }
        log.debug("session token >" +serverToken +" page token >" + pageToken);
        //2、如果当前用户的Session中不存在Token(令牌)，则用户是重复提交了表单
        if(serverToken==null){
            return true;
        }
        //3、存储在Session中的Token(令牌)与表单提交的Token(令牌)不同，则用户是重复提交了表单
        if(!pageToken.equals(serverToken)){
            return true;
        }
        
        return false;
    }
	
    private void checkToken(Context context) throws ValidationException{
    	if (isRepeatSubmit(context)) {
			throw new ValidationException(Checkmsg.REQUEST_TOKEN_iNVALID);
		}
    	String token = TokenProccessor.getInstance().makeToken();//创建新token
		log.debug("生成 token >" + token);
    	context.getSession().setAttribute("_token", token);
	}
    
    @Override
	public Object issueTrsHost(Context context, Map paramMap) {
		prepareCommonFields(context,paramMap);
		return null;
	}
	
	private void prepareCommonFields(Context context, Map paramMap){
		paramMap.put("_TransName", context.getTransactionId());
		paramMap.put("_JnlNo", context.getTransactionId());
		paramMap.put("_TransTime", System.currentTimeMillis());
		
		//paramMap.put(Dict.HOSTTRANSCODE, value)
	}
}