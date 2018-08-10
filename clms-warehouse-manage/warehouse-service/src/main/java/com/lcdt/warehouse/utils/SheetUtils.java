package com.lcdt.warehouse.utils;

import java.sql.Date;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author Sheng-ji Lau
 * @date 2018年8月10日
 * @version
 * @Description: TODO 
 */
public class SheetUtils {
	
	private XSSFWorkbook xwb;
	
	private XSSFSheet sheet;
	
	private XSSFRow row ;
	
	private XSSFCell cell;
	
	public SheetUtils(XSSFWorkbook xwb) {
		this.xwb = xwb;
	}
	
	public SheetUtils(XSSFWorkbook xwb, Integer sheetNum) {
		this.xwb = xwb;
		this.sheet = xwb.getSheetAt(sheetNum);
	}
	
	public SheetUtils(XSSFWorkbook xwb, Integer sheetNum, Integer rowNum) {
		this.xwb = xwb;
		this.sheet = xwb.getSheetAt(sheetNum);
		this.row = sheet.getRow(rowNum);
	}
	
	public SheetUtils(XSSFWorkbook xwb, Integer sheetNum, Integer rowNum,  Integer cellNum) {
		this.xwb = xwb;
		this.sheet = xwb.getSheetAt(sheetNum);
		this.row = sheet.getRow(rowNum);
		this.cell = row.getCell(cellNum);
	}
	
	public void setCell(Integer rowNum, Integer cellNum, Object object) {
		row = sheet.getRow(rowNum);
		cell = row.getCell(cellNum);
		setCellValue(object);
	}
	
	
	public void setCell(Integer cellNum, Object object) {
		cell = row.getCell(cellNum);
		setCellValue(object);
	}
	
	
	public void setCellValue(Object object) {
		if (object == null) {
			cell.setCellValue("");
			return;
		}
		if (object.getClass() == String.class) {
			cell.setCellValue((String)object);
			return;
		}
		if (object.getClass() == Double.class) {
			cell.setCellValue((Double)object);
			return;
		}
		if (object.getClass() == Date.class) {
			cell.setCellValue((Date)object);
		}
	}
	
	
	
	

}
