package com.emi.sys.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.emi.common.util.CommonUtil;

public class SqlUtil {
	/**
	 * update的sql用到的拼接语句 2014年5月27日 下午3:54:29 朱晓陈
	 * 
	 * @param param
	 *            参数名，首字母小写，第二个字母大写 如：cName ，get方法为getcName()
	 * @param clz
	 * @param obj
	 * @return String 例: cName='Tom',
	 */
	public static String updateParamSql(String param, Object obj)
	{
		return updateParamSql(param, obj, true);
	}

	/**
	 * update的sql用到的拼接语句,遇到null值判断是否更新 2014年5月27日 下午3:54:29 朱晓陈
	 * 
	 * @param param
	 *            参数名，首字母小写，第二个字母大写 如：cName ，get方法为getcName()
	 * @param obj
	 *            实体对象
	 * @param updNull
	 *            是否更新null值
	 * @return String 例: cName='Tom',
	 */
	public static String updateParamSql(String param, Object obj,
			boolean updNull)
	{
		try
		{
			Method method = obj.getClass().getMethod("get" + param);
			String sql = "";
			if (method.invoke(obj) != null)
			{
				if (method.invoke(obj) instanceof Timestamp)
				{
					// 时间类型单独转换
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy/MM/dd HH:mm:ss");
					String d = sdf.format(new Date(((Timestamp) method
							.invoke(obj)).getTime()));
					sql = " " + param + "='" + d + "'" + ",";
				} else
				{
					sql = " " + param + "='" + method.invoke(obj) + "'" + ",";
				}
			} else if (updNull)
			{
				sql = " " + param + "=null" + ",";
			}
			return sql;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * insert的sql用到的拼接语句 2014年5月27日 下午3:54:29 朱晓陈
	 * 
	 * @param param
	 *            参数名，首字母小写，第二个字母大写 如：cName ，get方法为getcName()
	 * @param clz
	 * @param obj
	 * @return String
	 */
	public static String insertParamSql(String param, Object obj)
	{
		try
		{
			Method method = obj.getClass().getMethod("get" + param);
			String sql = "";
			if (method.invoke(obj) != null)
			{
				if (method.invoke(obj) instanceof Timestamp)
				{
					// 时间类型单独转换
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy/MM/dd HH:mm:ss");
					String d = sdf.format(new Date(((Timestamp) method
							.invoke(obj)).getTime()));
					sql = " '" + d + "'" + ",";
				} else
				{
					sql = " '" + method.invoke(obj) + "'" + ",";
				}

			} else
			{
				sql = " null" + ",";
			}
			// System.out.println(sql);
			return sql;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * [适用于单表查询]
	 * 将bean的内容转成查询条件(模糊查询)
	 * 2014年8月5日 上午9:12:50 朱晓陈
	 * @param bean 对象
	 * @return
	 */
	public static String getWhere(Object bean){
		return getWhere(bean,"",true);
	}
	
	/**
	 * [适用于联合查询]
	 * 将bean的内容转成查询条件(模糊查询)
	 * 2014年8月5日 上午9:12:50 朱晓陈
	 * @param bean 对象
	 * @param alias 别名 (不需要则null或空字符串)
	 * @return
	 */
	public static String getWhere(Object bean,String alias){
		return getWhere(bean,alias,true);
	}
	
	/**
	 * 
	 * 2014年8月5日 上午9:15:39 朱晓陈
	 * @param bean
	 * @param isFuzzy 是否支持模糊查询
	 * @return
	 */
	public static String getWhere(Object bean,String alias,boolean isFuzzy){
		String where = "";
		String prefix = "";
		if(!CommonUtil.isNullString(alias)){
			prefix = alias+".";
		}
		try {
			Field[] fields=bean.getClass().getDeclaredFields();
			for(int j=0;j<fields.length;j++){
				Field field = fields[j];
				String pro = field.getName();
				String getMtd = "get"+pro.substring(0,1).toUpperCase()+pro.substring(1);
				Method method = bean.getClass().getMethod(getMtd);
				Object val = method.invoke(bean);
				if(val instanceof String){
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return where;
	}
}
