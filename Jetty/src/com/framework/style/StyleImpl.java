package com.framework.style;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StyleImpl implements Style{
	protected Log log = LogFactory.getLog(this.getClass()) ;
	private Map<String, String> settings;//配置项
	private Convertor convertor;//配置项
	
	public Map<String, String> getSettings() {
		return settings;
	}

	public void setSettings(Map<String, String> settings) {
		this.settings = settings;
	}

	@Override
	public void setConvertor(Convertor convertor) {
		this.convertor = convertor;
	}

	@Override
	public Convertor getConvertor() {
		return this.convertor;
	}
}
