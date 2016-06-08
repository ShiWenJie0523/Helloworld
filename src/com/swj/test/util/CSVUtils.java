/**
 * @{#} WriteExcel.java Create on 2016年5月20日 下午1:55:44
 *
 * Copyright (c) 2016 by JRJ. 
 */

package com.swj.test.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 * 
 * CSV文件导出工具类
 *
 * @author
 * @reviewer
 */
public class CSVUtils {

	/**
	 * CSV文件生成方法
	 * 
	 * @param head
	 * @param dataList
	 * @param outPutPath
	 * @param filename
	 * @return
	 */
	@SuppressWarnings("unused")
	public static File createCSVFile(List<Object> head, List<Object> dataList, String outPutPath,
			String filename, boolean flag) {

		File csvFile = null;
		BufferedWriter csvWtriter = null;
		try {
			csvFile = new File(outPutPath + File.separator + filename + ".csv");
			File parent = csvFile.getParentFile();
			if (parent != null && !parent.exists()) {
				parent.mkdirs();
			}
			if(csvFile == null && !csvFile.exists()){
				csvFile.mkdir();
			}

			// GB2312使正确读取分隔符","
			csvWtriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile, true), "GB2312"), 1024);
			if(flag){
				// 写入文件头部
				writeRow(head, csvWtriter);
			}

			// 写入文件内容
			writeRow(dataList, csvWtriter);
			csvWtriter.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				csvWtriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return csvFile;
	}

	/**
	 * 写一行数据方法
	 * 
	 * @param row
	 * @param csvWriter
	 * @throws IOException
	 */
	private static void writeRow(List<Object> row, BufferedWriter csvWriter) throws IOException {
		// 写入文件头部
		int count = row.size();
		for (int i =0;i< count;i++) {
			Object data = row.get(i);
			StringBuffer sb = new StringBuffer();
			String rowStr = null;
			if(i != count-1){
				rowStr = sb.append("\"").append(data == null?"":data).append("\",").toString();
			}else{
				rowStr = sb.append("\"").append(data == null?"":data).append("\"").toString();
			}
			
			csvWriter.write(rowStr);
		}
		csvWriter.newLine();
	}
}
