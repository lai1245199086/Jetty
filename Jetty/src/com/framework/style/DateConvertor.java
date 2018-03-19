package com.framework.style;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.framework.util.StringUtil;

public class DateConvertor implements Convertor{
	protected Log log = LogFactory.getLog(this.getClass()) ;
	@Override
	public Object convert(Object object) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		if (null!=object&&StringUtil.isNotEmpty(object.toString())) {
			try {
				Date date = format.parse(object.toString());
				return date;
			} catch (ParseException e) {
				e.printStackTrace();
				log.info("转换异常");
			}
		}
		return null;
	}

}
