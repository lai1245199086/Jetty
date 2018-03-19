package com.framework.style;

import java.util.Map;

public interface Style{
	public void setConvertor(Convertor convertor);
	public Convertor getConvertor();
	public Map<String, String> getSettings();
	public void setSettings(Map<String, String> settings);
}
