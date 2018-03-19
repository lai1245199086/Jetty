package com.framework.action;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mybatis.spring.SqlSessionTemplate;

import com.framework.constants.Dict;
import com.framework.context.Context;

public abstract class IbsQueryAction implements BaseQueryAction{
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
	public void execute(Context context) throws Exception {
		this.query(context);
	}
	
	@Override
	public Object issueTrsHost(Context context, Map paramMap) {
		prepareCommonFields(context,paramMap);
		return null;
	}
	
	private void prepareCommonFields(Context context, Map paramMap){
		paramMap.put("_JnlNo", context.getTransactionId());
		paramMap.put("_TransName", context.getTransactionId());
		paramMap.put("_TransTime", System.currentTimeMillis());
		
		//paramMap.put(Dict.HOSTTRANSCODE, value)
	}
	
}