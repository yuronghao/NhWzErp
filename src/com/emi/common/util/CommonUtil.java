/*
 * Title: Tools.java
 * Company: EMI
 * Author: xiaochen zhu
 * Date: May 18, 2014 8:27:36 PM
 * Version: 4.0
 */
package com.emi.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.util.SysPropertites;

/**
 * 常用工具类.
 * 
 * @version 1.0
 * @since 2014-5-14
 */
@SuppressWarnings("unchecked")
public class CommonUtil {
	/**
	 * 获取根目录
	 * 
	 * @return 项目的根路径
	 */
	public static String getRootPath() {
		String result = null;
		try {
			result = CommonUtil.class.getResource("CommonUtil.class").toURI()
					.getPath().toString();
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
		int index = result.indexOf("WEB-INF");
		if (index == -1) {
			index = result.indexOf("bin");
		}
		if(WindowOrLinux()){//windows系统要截取第一个“/”
			result = result.substring(1, index);
		}else{
			result = result.substring(0, index);
		}
		if (result.endsWith("/"))
			result = result.substring(0, result.length() - 1);// 不包含最后的"/"
		return result;
	}
	
	public static String getFileRootPath(){
		String path = "";
		if(WindowOrLinux()){
			path = SysPropertites.get("file.root");
		}else{
			path = SysPropertites.get("linux.file.root");
		}
		return path;
	}

	/**
	 * 判断字符串是否为空.
	 * 
	 * @param str
	 *            the str
	 * 
	 * @return boolean
	 */
	public static boolean isNullString(String str) {
		return null == str || "".equals(str.trim());
	}
	public static boolean notNullString(String str){
		return !isNullString(str);
	}
	/**
	 * Checks if is null string.
	 * 
	 * @param str1
	 *            the str1
	 * @param str2
	 *            the str2
	 * 
	 * @return true, if is null string
	 */
	public static boolean isNullString(String str1, String str2) {
		return isNullString(str1) || isNullString(str2);
	}

	/**
	 * 调试信息
	 * 
	 * @param obj
	 */
	public static void debug(Object obj) {
		System.out.println("[debug]: " + obj.toString());
	}

	/**
	 * 压缩字符串中的空白字符
	 * 
	 * @param str
	 * @return
	 */
	public static String compressBlank(String str) {
		str = str.trim();
		if (CommonUtil.isNullString(str))
			return "";
		StringBuilder str_bu = new StringBuilder();
		char[] str_arr = str.toCharArray();
		for (int i = 0; i < str_arr.length; i++) {
			if (!isBlank(str_arr[i]))
				str_bu.append(str_arr[i]);
			else if (isBlank(str_arr[i]) && i + 1 < str_arr.length
					&& !isBlank(str_arr[i + 1]))
				str_bu.append((char) 32);
		}
		return str_bu.toString();
	}

	/**
	 * 判断某字符是否是空白字符
	 * 
	 * @param c
	 * @return
	 */
	public static boolean isBlank(char c) {
		return (int) c == 9 || (int) c == 32;
	}

	/**
	 * 如果字符串为null,返回""
	 * 
	 * @param username
	 * @return
	 */
	public static String null2Str(String str) {
		return str == null ? "" : str;
	}

	/**
	 * 判断输入的对象是否为null，如果是null则返回"",否则，返回 对象.toString()。
	 * 
	 * @param str
	 * @return
	 */
	public static String Obj2String(Object obj) {
		return obj == null ? "" : obj.toString();
	}
	

	/**
	 * 判断输入的时间是否为null，如果是null则返回"",否则，返回 时间.toString()。
	 * */
	public static String null2Timestamp(Timestamp sts) {
		return sts == null ? "" : sts.toString();
	}

	/**
	 * 将list<Map> 转换成string
	 * 
	 * @param list
	 * @return string
	 */
	public static String listMap2String(List<Map<String, Object>> list) {
		StringBuilder str_to_return = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = list.get(i);
			Set<String> keys = map.keySet();
			// int j = 0;
			for (String key : keys) {
				str_to_return.append(key).append(":=:");
				str_to_return.append(map.get(key));
				// if (j++ < keys.size() - 1)
				str_to_return.append(":==:");
			}
			str_to_return.append(":===:");
		}
		return str_to_return.toString();
	}

	/**
	 * 将map 转换成string
	 * 
	 * @param map
	 * @return string
	 */
	public static String map2String(Map<String, Object> map) {
		StringBuilder str_to_return = new StringBuilder();
		Set<String> keys = map.keySet();
		for (String key : keys) {
			str_to_return.append(key).append(":=:");
			str_to_return.append(map.get(key));
			// if (j++ < keys.size() - 1)
			str_to_return.append(":==:");
		}
		return str_to_return.toString();
	}

	/**
	 * 判断当前系统是WINDOWS还是LINUX
	 * 
	 * @param
	 * @return true windows false linux
	 */
	public static boolean WindowOrLinux() {
		if (System.getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1) {
			return true;
		}
		return false;
	}

	/**
	 * 如果是Windows系统，替换路径
	 * 
	 * @param str
	 * @return
	 */
	public static String UrlReplace(String url) {
		return url.replaceAll("/", "\\\\");
	}

	/**
	 * 判断当前操作系统，如果是Windows系统，替换路径。
	 * 
	 * @param str
	 * @return
	 */
	public static String SysReplace(String url) {
		if (WindowOrLinux()) {
			return UrlReplace(url);
		} else {
			return url;
		}
	}

	public static long getLong(byte[] bb) {
		return ((((long) bb[0] & 0xff) << 56) | (((long) bb[1] & 0xff) << 48)
				| (((long) bb[2] & 0xff) << 40) | (((long) bb[3] & 0xff) << 32)
				| (((long) bb[4] & 0xff) << 24) | (((long) bb[5] & 0xff) << 16)
				| (((long) bb[6] & 0xff) << 8) | (((long) bb[7] & 0xff) << 0));
	}

	public static boolean isNullObject(Object object) {
		if (object == null || "".equals(object.toString())
				|| "null".equals(object.toString())) {
			return true;
		}
		return false;
	}
	public static boolean notNullObject(Object object) {
		return !isNullObject(object);
	}
	/**
	 * 数据层级化
	 * 2014年8月11日 下午5:48:54 
	 * @author 朱晓陈
	 * @param sort 需转成json的map
	 * @param list 总列表
	 * @param idProp id属性名
	 * @param pIdProp  关联父id属性名
	 */
	public static List<Map<String,Object>> hierarchicalChild(List<?> list,String idProp, String pIdProp){
		List<Map<String,Object>> newSortList = new ArrayList<Map<String,Object>>();//分级后的权限

		try {
			for (Object obj : list) {
				String pIdMtd = "get" + pIdProp.substring(0, 1).toUpperCase()
						+ pIdProp.substring(1);
				Method pIdMethod = obj.getClass().getMethod(pIdMtd); // 父id的get方法
				Object pIdVal = pIdMethod.invoke(obj); // 父id的值
				if (CommonUtil.isNullObject(pIdVal) || "0".equals(pIdVal)) {
					Field[] fields = obj.getClass().getDeclaredFields();
					// 第一层节点
					Map<String, Object> sort = new HashMap<String, Object>();
					for (int n = 0; n < fields.length; n++) {
						Field field = fields[n];
						if (field.isAnnotationPresent(EmiColumn.class)) {
							String mapKey = field.getName();
							String getMtd = "get"
									+ mapKey.substring(0, 1).toUpperCase()
									+ mapKey.substring(1);
							Method method = obj.getClass().getMethod(getMtd);
							Object mapVal = method.invoke(obj);
							sort.put(mapKey, mapVal);
						}
					}
					// 迭代子节点
					CommonUtil.sortChild(sort, list, idProp, pIdProp);
					newSortList.add(sort);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newSortList;
	}


	private static void sortChild(Map<String, Object> sort, List<?> list,
			String idProp, String pIdProp) {
		try {
			List<Map> childList = null;
			String pUid = CommonUtil.Obj2String(sort.get(idProp));
			for (Object obj : list) {
				String pIdMtd = "get" + pIdProp.substring(0, 1).toUpperCase()
						+ pIdProp.substring(1);
				Method pIdMethod = obj.getClass().getMethod(pIdMtd); // 父id的get方法
				Object pId_c_Val = pIdMethod.invoke(obj); // 父id的值
				if (CommonUtil.Obj2String(pId_c_Val).equals(pUid)) {
					Field[] fields = obj.getClass().getDeclaredFields();
					// 子节点
					Map<String, Object> sort_child = new HashMap<String, Object>();
					for (int n = 0; n < fields.length; n++) {
						Field field = fields[n];
						if (field.isAnnotationPresent(EmiColumn.class)) {
							String mapKey = field.getName();
							String getMtd = "get"
									+ mapKey.substring(0, 1).toUpperCase()
									+ mapKey.substring(1);
							Method method = obj.getClass().getMethod(getMtd);
							Object mapVal = method.invoke(obj);
							sort_child.put(mapKey, mapVal);
						}
					}

					// 迭代子节点
					sortChild(sort_child, list, idProp, pIdProp);
					if (childList == null) {
						childList = new ArrayList<Map>();
					}
					childList.add(sort_child);
				}
			}
			// 将子节点列表加入父节点
			sort.put("childNodes", childList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 从实体类中得到表中所有select 语句可用的字段，并拼成带逗号的sql字符串 2014年8月11日 下午5:48:50
	 * 
	 * @author 朱晓陈
	 * @param clazz
	 *            实体类
	 * @return
	 */
	public static String colsFromBean(Class<?> clazz) {
		return colsFromBean(clazz,  "");
	}
	/**
	 * 从实体类中得到表中所有select 语句可用的字段，并拼成带逗号的sql字符串 2014年8月11日 下午5:48:50
	 * 
	 * @author 朱晓陈
	 * @param clazz
	 *            实体类
	 * @param prefix
	 *            表别名 如：t.name 别名为't'
	 * @return
	 */
	public static String colsFromBean(Class<?> clazz, String prefix) {
		String sel_cols = "";
		Field[] fields = clazz.getDeclaredFields();
		StringBuffer columns = new StringBuffer();
		for (int j = 0; j < fields.length; j++) {
			Field field = fields[j];
			// 加注解的才自动注入值
			if (field.isAnnotationPresent(EmiColumn.class)) {
				EmiColumn column = (EmiColumn) field
						.getAnnotation(EmiColumn.class);
				String columnName = column.name(); // 列名
				if (CommonUtil.isNullString(prefix)) {
					columns.append(columnName).append(",");
				} else {
					columns.append(prefix).append(".").append(columnName)
							.append(",");
				}

			}
		}
		if (columns.length() > 0) {
			sel_cols = " " + columns.substring(0, columns.length() - 1) + " ";
		}
		return sel_cols;
	}

	

	
	/**
	 * @author 武晋博
	 * @param is
	 * @return 2014年8月15日
	 * @excel 相关导入
	 */
	public static List<String[]> getAllExcelData(File file) {
		List<String[]> list = new ArrayList<String[]>();
		try {
			Workbook book = null;
			boolean isExcel2003 = true;
			 /*if (isExcel2007(file)) { isExcel2003 = false; }
			 
			if (isExcel2003) {
				book = new HSSFWorkbook(is);
			} else {
				book = new XSSFWorkbook(is);
			}*/
			try {
				book = new HSSFWorkbook(new FileInputStream(file));
			} catch (Exception e) {
				book = new XSSFWorkbook(new FileInputStream(file));
			}finally{
				Sheet sheet = book.getSheetAt(0);
				int rows = sheet.getPhysicalNumberOfRows();
				int cells = 0;
				if (rows >= 1 && sheet.getRow(0) != null) {
					cells = sheet.getRow(0).getPhysicalNumberOfCells();
				}

				for (int i = 1; i < rows; i++) {
					Row row = sheet.getRow(i);
					if (row == null) {
						continue;
					}
					String[] str = new String[cells];
					for (int j = 0; j < cells; j++) {
						Cell cell = row.getCell(j);

						String cellValue = "";

						if (null != cell) {
							// 以下是判断数据的类型
							switch (cell.getCellType()) {
							case HSSFCell.CELL_TYPE_NUMERIC: // 数字
								cellValue = cell.getNumericCellValue() + "";
								break;

							case HSSFCell.CELL_TYPE_STRING: // 字符串
								cellValue = cell.getStringCellValue();
								break;

							case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
								cellValue = cell.getBooleanCellValue() + "";
								break;

							case HSSFCell.CELL_TYPE_FORMULA: // 公式
//									cellValue = cell.getCellFormula() + "";
								cellValue = cell.getNumericCellValue() + "";
								break;

							case HSSFCell.CELL_TYPE_BLANK: // 空值
								cellValue = "";
								break;
							case HSSFCell.CELL_TYPE_ERROR: // 故障
								cellValue = "非法字符";
								break;
							default:
								cellValue = "未知类型";
								break;
							}
						}
						str[j] = cellValue;
					}
					list.add(str);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}
	
	/**
	 * 获得小数位数
	 * syh
	 * @param d
	 * @param round
	 * @return
	 */
	public static double getDoubleRound(double d,int round){
		
		BigDecimal  bigd  =  new  BigDecimal(d);
		double result=bigd.setScale(round, BigDecimal.ROUND_HALF_UP).doubleValue();
		return result;
	}
	
	/**
	 * @category 组合查询sql
	 * 2014年11月25日 下午1:20:51 
	 * @author 朱晓陈
	 * @param columns 字段，多个逗号隔开
	 * @param keyWord 查询关键字
	 * @return
	 */
	public static String combQuerySql(String columns,String keyWord){
		String sql = " and (";
		if(!CommonUtil.isNullString(columns) && !CommonUtil.isNullString(keyWord)){
			String[] cols = columns.split(",");
			for(String col:cols){
				sql += " "+col+" like '%"+keyWord+"%' or";
			}
		}
		if(sql.length()>6){
			sql = sql.substring(0,sql.length()-2)+") ";
		}else{
			sql = "";
		}
		return sql;
	}
	public static String combQuerySql(String columns,String keyWord,String extSql){
		String sql = " and (";
		if(!CommonUtil.isNullString(columns) && !CommonUtil.isNullString(keyWord)){
			String[] cols = columns.split(",");
			for(String col:cols){
				sql += " "+col+" like '%"+keyWord+"%' or";
			}
		}
		if(sql.length()>6){
			sql = sql.substring(0,sql.length()-2)+extSql+") ";
		}else{
			sql = "";
		}
		return sql;
	}

	/**
	 * 如果以指定字符串结尾，截掉该字符串
	 * @author 朱晓陈
	 * @param builders
	 * @param string
	 * @return
	 */
	public static String cutLastString(String sourceStr, String cutStr) {
 		if(sourceStr.length()>cutStr.length() && sourceStr.lastIndexOf(cutStr)==(sourceStr.length()-cutStr.length())){
			return sourceStr.substring(0,sourceStr.length()-cutStr.length());
		}else{
			return sourceStr;
		}
		
	}

	public static BigDecimal str2BigDecimal(String num) {
		if(CommonUtil.isNullString(num)){
			num = "0";
		}
		return new BigDecimal(num);
	}
	
	public static BigDecimal str2BigDecimal(Object num) {
		if(CommonUtil.isNullObject(num)){
			num = "0";
		}
		return new BigDecimal(num.toString());
	}
	
	
	public static void sortJsonArray(JSONArray ja, final String field,
			boolean isAsc) {
		Collections.sort(ja, new Comparator<JSONObject>() {
			@Override
			public int compare(JSONObject o1, JSONObject o2) {
				Object f1 = o1.get(field);
				Object f2 = o2.get(field);
				if (f1 instanceof Number && f2 instanceof Number) {
					return ((Number) f1).intValue() - ((Number) f2).intValue();
				} else {
					return f1.toString().compareTo(f2.toString());
				}
			}
		});
		if (!isAsc) {
			Collections.reverse(ja);
		}
	}
	
	public static BigDecimal null2BigDecimal(BigDecimal val,String defaultVal) {
		return null2BigDecimal(val,new BigDecimal(defaultVal));
	}

	public static BigDecimal null2BigDecimal(BigDecimal val,BigDecimal defaultVal) {
		return val==null?defaultVal:val;
	}
	
	public static BigDecimal null2BigDecimal(BigDecimal val) {
		return null2BigDecimal(val,new BigDecimal(0));
	}
	
	public static BigDecimal bigDecimal2BigDecimal(BigDecimal num) {
		if(CommonUtil.isNullObject(num)){
			return new BigDecimal(0);
		}
		return num;
	}
	
	
	public static BigDecimal object2BigDecimal(Object num) {
		if(CommonUtil.isNullObject(num)){
			return new BigDecimal(0);
		}
		return BigDecimal.valueOf(Double.valueOf(num.toString()));
	}
	
	public static BigDecimal object2BigDecimalOne(Object num) {
		if(CommonUtil.isNullObject(num)){
			return new BigDecimal(1);
		}
		return BigDecimal.valueOf(Double.valueOf(num.toString()));
	}
	
}