package com.framework.chain;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.framework.context.Context;
import com.framework.style.Style;
import com.framework.util.ResolverUtil;
import com.framework.util.StringUtil;
import com.framework.constants.Checkmsg;
import com.framework.exception.ValidationException;

public class ValidationCommand implements Command{
	protected Log log = LogFactory.getLog(this.getClass()) ;
	@Override
	public void execute(Context context)  throws Exception{
		LinkedHashMap<String, String> fieldsConfig= context.getActionConfig().getFields();
		if (null!=fieldsConfig)
		for (Map.Entry<String, String> entry : fieldsConfig.entrySet()) {
			String styleName = entry.getValue();
			int n = styleName.indexOf("{");
			String optionStr = null;
			if (n>-1) {
				int m = styleName.indexOf("}");
				int e = styleName.indexOf("=");
				optionStr = styleName.substring(e+1, m);
				styleName = styleName.substring(0, n);
			}
			Style style = ResolverUtil.getStyle(styleName);
			if (null==optionStr) {
				optionStr = style.getSettings().get("option");
			}
			Boolean option = Boolean.valueOf(optionStr);
			String rulePattern = style.getSettings().get("rulePattern");
			String text = (String)context.getData(entry.getKey());
			log.info(" Style = "+rulePattern+" , "+entry.getKey()+" = "+text);
			if (StringUtil.isEmpty(text)&&option) {
				return;
			}
			if (!text.matches(rulePattern)) {
				Object[] paramArgs = new Object[]{entry.getKey()};
				String messageKey = Checkmsg.FIELD_DOES_NOT_MATCH_REQUIRED_PATTERN;
				throw new ValidationException(messageKey,paramArgs);
			}
			if (null!=style.getConvertor()) {
				context.setData(entry.getKey(), style.getConvertor().convert(text));
			}
		}
	}
	
}
