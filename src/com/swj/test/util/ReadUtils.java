/**
 * @{#} ReadExcel.java Create on 2016年5月19日 上午9:42:48
 *
 * Copyright (c) 2016 by JRJ. 
 */

package com.swj.test.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * 
 * @history
 * 
 *          <PRE>
 *  
 * --------------------------------------------------------- 
 * VERSION       DATE            BY       CHANGE/COMMENT 
 * --------------------------------------------------------- 
 * 1.0           2016年5月19日       wenjie.shi               create  
 * ---------------------------------------------------------
 *          </PRE>
 *
 */

public class ReadUtils {
	// 记录类的输出信息
	static org.apache.commons.logging.Log log = LogFactory.getLog(ReadUtils.class);
	// 获取Excel文档的路径
	public static String filePath = "D://excel.xlsx";
	
	public static String csvFilePath = "D://test//上行数据.csv";
	
	private static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
	// 导出文件路径
	private static String outPutPath = "D:" + File.separator + "cap4j" + File.separator + "download" + File.separator;
	
	/**
	 * 创建cvs文件
	 * 
	 * @param val
	 * @throws ParseException
	 */
	public static void createFile(String[] val, boolean flag) {
		// 设置表格头
		Object[] head = { "FMSGID", "FWAITSENDID", "FUNIID", "FMsgType", "FOrgAddr", "FDestAddr", "FFeeUserType",
				"FFeeTerminal", "FContent", "FGatewayID", "FServiceID", "FServiceCode", "FCPSERVICEID", "FFEETYPEID",
				"FFEECODE", "FReportFlag", "FMTFlag", "FGIVENCODE", "FLinkID", "FSubmitStatus", "FSUBMITDESC",
				"FSubmitTime", "FREPORTSTATUS", "FREPORTDESC", "FREPORTTIME", "FState" };
		List<Object> headList = Arrays.asList(head);

		// 保存上行记录
		List<Object> dataList = MoDAO.queryMo(val, headList);

		// 导出文件名称
		String datetimeStr = sdf1.format(new Date());
		String fileName = "上行数据";
		CSVUtils.createCSVFile(headList, dataList, outPutPath, fileName, flag);
	}

	public static void createOrderUserFile(String[] val, boolean flag) {
		Object[] head = { "FMobile", "FFeeTerminal", "FServiceID", "FState", "FRegTime", "FCancelTime", "FLinkID",
				"FMark" };
		List<Object> headList = Arrays.asList(head);
		// 组装数据
		List<Object> dataList = OrderUserDAO.queryOrderUser(val, headList);

		// 导出文件名称
		String datetimeStr = sdf1.format(new Date());
		String fileName = "用户订购记录数据_" + datetimeStr;
		CSVUtils.createCSVFile(headList, dataList, outPutPath, fileName, flag);
	}

	/**
	 * 读取csv文件获取数据
	 * 
	 * @return
	 */
	public static String[] readCsvfile(String path) {
		String[] val = null;
		BufferedReader reader = null;
		String line;
		int count = 0;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
			while ((line = reader.readLine()) != null) {
				count++;
				val = line.split(",");
				
				 for (int j = 0; j < val.length; j++) { 
					 // 每一行列数中的值遍历出来
					 System.out.println(val[j]);
				 }
				 
				// 组装数据并导出到csv文件
				 if(count == 1){
					 createFile(val, true); 
				 }else{
					 createFile(val, false); 
				 }
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return val;
	}

	/***
	 * 读取excel文件获取数据
	 * 
	 * @return
	 */
	public static String[] readExcelFile() {
		String[] val = null;
		try {
			// 创建对Excel工作簿文件
			// HSSFWorkbook wookbook= new HSSFWorkbook(new
			// FileInputStream(filePath));
			// 在Excel文档中，第一张工作表的缺省索引是0，
			// 其语句为：HSSFSheet sheet = workbook.getSheetAt(0);
			// HSSFSheet sheet = wookbook.getSheet("Sheet1");
			DecimalFormat df = new DecimalFormat("0");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Workbook book = null;
			try {
				book = new XSSFWorkbook(filePath);
			} catch (Exception ex) {
				book = new HSSFWorkbook(new FileInputStream(filePath));
			}

			Sheet sheet = book.getSheet("Sheet1");
			// 获取到Excel文件中的所有行数
			int rows = sheet.getPhysicalNumberOfRows();
			// 遍历行
			for (int i = 0; i < rows; i++) {
				// 读取左上端单元格
				Row row = sheet.getRow(i);
				// 行不为空
				if (row != null) {
					// 获取到Excel文件中的所有的列
					int cells = row.getPhysicalNumberOfCells();
					String value = "";
					// 遍历列
					for (int j = 0; j < cells; j++) {
						// 获取到列的值
						Cell cell = row.getCell(j);
						if (cell != null) {
							switch (cell.getCellType()) {
							case Cell.CELL_TYPE_FORMULA:
								break;
							case Cell.CELL_TYPE_NUMERIC:
								if (j == cells - 1) {
									value += sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
								} else {
									value += df.format(cell.getNumericCellValue()) + ",";
								}
								break;
							case Cell.CELL_TYPE_STRING:
								value += cell.getStringCellValue() + ",";
								break;
							default:
								break;
							}
						}
					}

					val = value.split(",");
					for (int j = 0; j < val.length; j++) {
						// 每一行列数中的值遍历出来
						System.out.println(val[j]);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return val;
	}
	
	public static void main(String[] args) {
		String[] values = readCsvfile(csvFilePath);
		for (String string : values) {
			System.out.println(string);
		}
	}
}