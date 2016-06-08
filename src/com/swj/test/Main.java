/**
 * @{#} Main.java Create on 2016年5月11日 下午5:07:24
 *
 * Copyright (c) 2016 by JRJ. 
 */

package com.swj.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.List;

import com.swj.test.util.MoDAO;

/**
  *
  * 
  * @history 
  * <PRE> 
  * --------------------------------------------------------- 
  * VERSION       DATE            BY       CHANGE/COMMENT 
  * --------------------------------------------------------- 
  * 1.0           2016年5月11日       wenjie.shi               create  
  * ---------------------------------------------------------
  * </PRE>
  *
  */

public class Main {

	public static void main(String[] args) {
		/*System.out.println("hi,Helloworld");
		MyThread thread = new MyThread();
		thread.start();*/
		Object[] head = {"mobile","destaddr","content","linkid","serviceid","time"};
		List<Object> headList = Arrays.asList(head);
		long time = System.currentTimeMillis();
		List<List<Object>> list = MoDAO.queryList();
		long time1 = System.currentTimeMillis();
		System.out.println(time1 - time + "ms");
		System.out.println("取到数据的记录数：" + list.size());
		//生成数据csv文件
		String outPutPath = "D:" + File.separator + "test" + File.separator;
		String filename = "上行数据";
		createCSVFile(headList, list, outPutPath, filename);
		System.out.println("===========写入文件完成============");
	}
	
	
	/**
	 * CSV文件生成方法
	 * 
	 * @param head
	 * @param dataList
	 * @param outPutPath
	 * @param filename
	 * @return
	 */
	public static File createCSVFile(List<Object> head, List<List<Object>> dataList, String outPutPath,
			String filename) {

		File csvFile = null;
		BufferedWriter csvWtriter = null;
		try {
			csvFile = new File(outPutPath + File.separator + filename + ".csv");
			File parent = csvFile.getParentFile();
			if (parent != null && !parent.exists()) {
				parent.mkdirs();
			}
			csvFile.createNewFile();

			// GB2312使正确读取分隔符","
			csvWtriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "GB2312"), 1024);
			// 写入文件头部
			writeRow(head, csvWtriter);
			// 写入文件内容
			for (List<Object> list : dataList) {
				writeRow(list, csvWtriter);
			}
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
		for (Object data : row) {
			StringBuffer sb = new StringBuffer();
			String rowStr = sb.append(data == null?"":data).append(",").toString();
			csvWriter.write(rowStr);
		}
		csvWriter.newLine();
	}
}
