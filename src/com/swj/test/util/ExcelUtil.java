/**
 * @{#} ExcelUtil.java Create on 2016年6月17日 下午1:26:57
 *
 * Copyright (c) 2016 by JRJ. 
 */

package com.swj.test.util;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
  *
  * excel导出工具类
  * @history 
  * <PRE> 
  * --------------------------------------------------------- 
  * VERSION       DATE            BY       CHANGE/COMMENT 
  * --------------------------------------------------------- 
  * 1.0           2016年6月17日       wenjie.shi               create  
  * ---------------------------------------------------------
  * </PRE>
  *
  */

public class ExcelUtil {

	public static HSSFWorkbook export(List<List<Object>> list,List<Object> head){
		HSSFWorkbook work = new HSSFWorkbook();
		HSSFSheet sheet = work.createSheet();
		HSSFRow row = sheet.createRow(0);
		HSSFCellStyle style = work.createCellStyle();    
	    style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    for (int i = 0; i < head.size(); i++) {    
            HSSFCell cell = row.createCell(i);    
            cell.setCellValue(head.get(i).toString());    
            cell.setCellStyle(style);    
            sheet.autoSizeColumn(i);    
        }
	    
	    for (int i = 0; i < list.size(); i++) {    
            row = sheet.createRow(i + 1);
            
            List<Object> objs = list.get(i);
            for (int j = 0; j < objs.size(); j++) {
            	String value = null;
            	if(j == 0 || j == 1){
            		value = objs.get(j) == null?"全国":objs.get(j).toString();
            	}else{
            		value = objs.get(j).toString();
            	}
            	HSSFCell cell = row.createCell(j);
            	if(j == 3 || j == 4){
            		cell.setCellValue(Double.parseDouble(value));
            		HSSFCellStyle cellStyle = work.createCellStyle();
                    cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
                    cell.setCellStyle(cellStyle);
            	}else if(j== 5 || j == 6){
            		cell.setCellValue(Double.parseDouble(value));
            		HSSFCellStyle cellStyle = work.createCellStyle();
                    cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"));
                    cell.setCellStyle(cellStyle);
            	}else{
            		cell.setCellValue(value);
            	}
			}
        }  
	    
		return work;
	}
}
