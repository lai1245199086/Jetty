package com.framework.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.Reader;

public class HeadTemplate {
	private Reader fileReader;
	private String prefixPath="src/com/config/outbound/";
	public HeadTemplate(String fileName) {
		try {
			File file= new File(prefixPath+fileName);
			fileReader = new FileReader(file);
		} catch (FileNotFoundException e) {
			//InputStream inputStream = getClass().getResourceAsStream();
			throw new RuntimeException("File read fail, filePath >>" +prefixPath+fileName);
		} 
	}
	public Reader getFileReader() {
		return fileReader;
	}
	
}
