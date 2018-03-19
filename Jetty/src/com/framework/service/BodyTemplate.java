package com.framework.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

import org.springframework.util.StringUtils;

public class BodyTemplate {
	private Reader fileReader;
	private String prefixPath="src/com/config/outbound/";
	private String prefixFileName="f";
	private String sufffixFileName=".xml";
	public BodyTemplate(String fileName,String prefixName) {
		if (StringUtils.isEmpty(prefixName)) {
			this.prefixFileName = prefixName;
		}
		try {
			fileReader = new FileReader(prefixPath+prefixFileName+fileName+sufffixFileName);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("File read fail, filePath >>" + prefixPath+prefixFileName+fileName+sufffixFileName);
		} 
	}
	public Reader getFileReader() {
		return fileReader;
	}
	
}
