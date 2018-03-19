package com.framework.action;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;

import com.framework.context.Context;

public interface BaseAction {
	public abstract void execute(Context context) throws Exception;
	public abstract void setSqlMap(SqlSessionTemplate sqlMap);
	public abstract SqlSessionTemplate getSqlMap();
	public abstract Object issueTrsHost(Context context,Map paramMap);
}