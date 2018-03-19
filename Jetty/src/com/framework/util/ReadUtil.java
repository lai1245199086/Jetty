package com.framework.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.zip.GZIPInputStream;

public class ReadUtil {
	/***
	 * 从输入流获取字节数组,当文件很大时，报java.lang.OutOfMemoryError: Java heap space
	 * 
	 * @since 2014-02-19
	 * @param br_right
	 * @param length2
	 */
	public static byte[] readBytesFromInputStream(InputStream br_right, int length2) throws IOException {
		int readSize;
		byte[] bytes = null;
		bytes = new byte[length2];

		long length_tmp = length2;
		long index = 0;// start from zero
		while ((readSize = br_right.read(bytes, (int) index, (int) length_tmp)) != -1) {
			length_tmp -= readSize;
			if (length_tmp == 0) {
				break;
			}
			index = index + readSize;
		}
		return bytes;
	}

	
	/***
	 * 读取指定长度的字节
	 * 
	 * @since 2014-02-27
	 * @param ins
	 * @param sumLeng : 要读取的字节数
	 * @throws IOException
	 */
	public static byte[] readBytesFromGzipInputStream(GZIPInputStream ins, long sumLeng) throws IOException {
		byte[] fileNameBytes = new byte[(int) sumLeng];
		int fileNameReadLength = 0;
		int hasReadLength = 0;// 已经读取的字节数
		while ((fileNameReadLength = ins.read(fileNameBytes, hasReadLength, (int) sumLeng - hasReadLength)) > 0) {
			hasReadLength = hasReadLength + fileNameReadLength;
		}
		return fileNameBytes;
	}

	/***
	 * read char array from inputstream according to specified length.
	 * 
	 * @param file
	 * @param ins
	 * @param length2  :要读取的字符总数
	 * @throws IOException
	 */
	public static char[] getCharsFromInputStream(BufferedReader br_right, int length2) throws IOException {
		int readSize;
		char[] chars = null;
		chars = new char[length2];

		long length_tmp = length2;
		long index = 0;// start from zero
		while ((readSize = br_right.read(chars, (int) index, (int) length_tmp)) != -1) {
			length_tmp -= readSize;
			if (length_tmp == 0) {
				break;
			}
			index = index + readSize;// 写入字符数组的offset（偏移量）
		}
		return chars;
	}

	/***
	 * 从文件中读取指定长度的字符（注意：不是字节）
	 * 
	 * @param file
	 * @param length2
	 * @return
	 * @throws IOException
	 */
	public static char[] getCharsFromFile(File file, int length2) throws IOException {
		FileInputStream fin = new FileInputStream(file);
		InputStreamReader inr = new InputStreamReader(fin);
		BufferedReader br = new BufferedReader(inr);
		return getCharsFromInputStream(br, length2);
	}

	private static byte[] readDataFromLength(HttpURLConnection huc, int contentLength) throws Exception {

		InputStream in = huc.getInputStream();
		BufferedInputStream bis = new BufferedInputStream(in);

		// 数据字节数组
		byte[] receData = new byte[contentLength];

		// 已读取的长度
		int readAlreadyLength = 0;

		// while ((readAlreadyLength+= bis.read(receData, readAlreadyLength,
		// contentLength-readAlreadyLength))< contentLength) {
		// System.out.println("right");
		// }
		while ((readAlreadyLength = readAlreadyLength + bis.read(receData, readAlreadyLength, contentLength - readAlreadyLength)) < contentLength) {
		}
		// System.out.println("readLength=" + readLength);
		return receData;
	}
	
	/**
	 * socket输入流中读取一个固定长度的字节
	 */
	public static  byte[] readFixedBytes(InputStream inputStream,int length,String charset) throws SocketTimeoutException, IOException{  
		BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
		InputStreamReader reader = new InputStreamReader(bufferedInputStream,charset); 
		char[] chars = new char[length];  
		reader.read(chars); 
		Charset cs = Charset.forName (charset);
	    CharBuffer cb = CharBuffer.allocate (chars.length);
		cb.put(chars);
		cb.flip ();
		ByteBuffer bb = cs.encode(cb);
		return bb.array();
	} 
	
	/*
	 * java 合并两个byte数组  
	 */
    public static byte[] byteMerger(byte[] byte_1, byte[] byte_2){  
        byte[] byte_3 = new byte[byte_1.length+byte_2.length];  
        System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);  
        System.arraycopy(byte_2, 0, byte_3, byte_1.length, byte_2.length);  
        return byte_3;  
    }  
}
