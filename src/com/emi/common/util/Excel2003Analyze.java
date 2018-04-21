package com.emi.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFDateUtil; 


/**
 * 解析Excel2003
 */
public class Excel2003Analyze {
	private HSSFWorkbook workbook;// 工作簿

	public Excel2003Analyze(String path) {
		try {
			File file = new File(path);
			// 获取工作薄workbook
			workbook = new HSSFWorkbook(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取指定的sheet中 所有数据
	 */
	public List<String[]> getDatasInSheet(int sheetNumber) {
		List<String[]> result = new ArrayList<String[]>();
		// 获得指定的sheet
		HSSFSheet sheet = workbook.getSheetAt(sheetNumber);
		// 获得sheet总行数
		int rowCount = sheet.getLastRowNum();
		
		if (rowCount < 1) {
			return result;
		}

		// 遍历行row
		for (int rowIndex = 0; rowIndex <= rowCount; rowIndex++) {
			// 获得行对象
			HSSFRow row = sheet.getRow(rowIndex);
			if (null != row) {
				// 获得本行中单元格的个数
				int cellCount = row.getLastCellNum();
				String[] rowData = new String[cellCount];
				// 遍历列cell
				for (short cellIndex = 0; cellIndex < cellCount; cellIndex++) {
					HSSFCell cell = row.getCell(cellIndex);
					// 获得指定单元格中的数据
					Object cellStr = this.getCellString(cell);
					rowData[cellIndex]=cellStr.toString();
				}
				result.add(rowData);
			}
		}

		return result;
	}

	/**
	 * 获得指定单元格中的数据
	 */
	private Object getCellString(HSSFCell cell) {
		Object result = null;
		if (cell != null) {
			// 单元格类型：Numeric:0,String:1,Formula:2,Blank:3,Boolean:4,Error:5
			int cellType = cell.getCellType();
			switch (cellType) {
			case HSSFCell.CELL_TYPE_STRING:
				result = cell.getStringCellValue();
				break;
			//case HSSFCell.CELL_TYPE_NUMERIC:
			//	result = cell.getNumericCellValue();
			//	break;
			case HSSFCell.CELL_TYPE_FORMULA:
				result = cell.getNumericCellValue();
				break;
			case HSSFCell.CELL_TYPE_BOOLEAN:
				result = cell.getBooleanCellValue();
				break;
			case HSSFCell.CELL_TYPE_BLANK:
				result = null;
				break;
			case HSSFCell.CELL_TYPE_ERROR:
				result = null;
				break;
			case HSSFCell.CELL_TYPE_NUMERIC:      
				if (HSSFDateUtil.isCellDateFormatted((HSSFCell) cell)) {      
					double d = cell.getNumericCellValue();      
					Date date = HSSFDateUtil.getJavaDate(d);
					result = DateUtil.dateToString(date, "yyyy-MM-dd HH:mm:ss");
					//tdList.add(DateUtil.convertDateToString("yyyy-MM-dd HH:mm:ss", date));  
				} else {  
					result = cell.getNumericCellValue();
					//tdList.add(String.valueOf((long)c.getNumericCellValue()));   
				}  
				break;
			default:
				System.out.println("枚举了所有类型");
				break;
			}

		}
		return result;
	}

	/**
	 * 获取指定的sheet中 行数
	 */
	public int getRowNum(int sheetNumber){
		// 获得指定的sheet
		HSSFSheet sheet = workbook.getSheetAt(sheetNumber);
		// 获得sheet总行数
		int rowCount = sheet.getLastRowNum();
		if (rowCount < 1) {
			return 0;
		}
		return rowCount;
	}
	
	/**
	 * 获取指定的sheet中 列数
	 */
	public int getColNum(int sheetNumber){
		// 获得指定的sheet
		HSSFSheet sheet = workbook.getSheetAt(sheetNumber);
		// 获得sheet总行数
		int rowCount = sheet.getLastRowNum();
		if (rowCount < 1) {
			return 0;
		}

		HSSFRow row0 = sheet.getRow(0);
		// 获得本行中单元格的个数
		int cellCount = row0.getLastCellNum();//获取第一行单元格数，即指定sheet列数
		return cellCount;
	}

	/**
	 * 校验格式、数据类型是否正确
	 * 
	 * 第一行不参与数值校验
	 * 
	 * return -1格式不正确；-2数据类型不正确；0没有数据
	 */
	public int checkFile(int sheetNumber){
		int checkFlag=0;
		// 获得指定的sheet
		HSSFSheet sheet = workbook.getSheetAt(sheetNumber);
		// 获得sheet总行数
		int rowCount = sheet.getLastRowNum();
		if (rowCount<2) {
			return 0;
		}

		HSSFRow row0 = sheet.getRow(0);
		if (null != row0) {
			// 获得本行中单元格的个数
			int cellCount = row0.getLastCellNum();
			checkFlag=cellCount;//获取第一行单元格数，即指定sheet列数
		}else{
			return -1;
		}
		
		// 遍历行row
		for (int rowIndex = 0; rowIndex <= rowCount; rowIndex++) {
			// 获得行对象
			HSSFRow row = sheet.getRow(rowIndex);
			if (null != row) {
				// 获得本行中单元格的个数
				int cellCount = row.getLastCellNum();
				if(cellCount!=checkFlag){//判断每一行列数是否和第一行一致
					return -1;
				}else{
					if(rowIndex!=0){
						// 遍历列cell
						for (short cellIndex = 0; cellIndex < cellCount; cellIndex++) {
							HSSFCell cell = row.getCell(cellIndex);
							// 获得指定单元格中的数据
							if (cell != null) {
								// 单元格类型：Numeric:0,String:1,Formula:2,Blank:3,Boolean:4,Error:5
								int cellType = cell.getCellType();
								if(cellType!=HSSFCell.CELL_TYPE_NUMERIC && cellType!=HSSFCell.CELL_TYPE_FORMULA){//判断除第一行外其他行的数据类型是否是数值型
									return -2;
								}
							}else{
								return -2;
							}
						}
					}
				}
			}else{
				return -1;
			}
		}
		return checkFlag;
	}


}
