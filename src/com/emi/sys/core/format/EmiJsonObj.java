package com.emi.sys.core.format;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.JSONUtils;

import com.emi.common.util.CommonUtil;

public class EmiJsonObj {
	/**
	 * @category 格式化后jsonObject
	 * 2014年9月10日 上午11:21:02 
	 * @author 朱晓陈
	 * @param obj
	 * @return
	 */
	public static JSONObject fromObject(Object obj){
		//json配置
		JsonConfig jsonCfg = new JsonConfig();
		jsonCfg.registerJsonValueProcessor(Timestamp.class,
				new JsonValueProcessor()
				{
					@Override
					public Object processObjectValue(String arg0,
							Object arg1, JsonConfig arg2)
					{
						//时间格式化
						SimpleDateFormat formater = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");
						if(CommonUtil.isNullObject(arg1)){
							return "";
						}
						return formater.format(arg1);
					}

					@Override
					public Object processArrayValue(Object arg0,
							JsonConfig arg1)
					{
						return null;
					}
				});
		jsonCfg.registerJsonValueProcessor(Date.class,
				new JsonValueProcessor()
				{
					@Override
					public Object processObjectValue(String arg0,
							Object arg1, JsonConfig arg2)
					{
						//时间格式化
						SimpleDateFormat formater = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");
						if(CommonUtil.isNullObject(arg1)){
							return "";
						}
						return formater.format(arg1);
					}

					@Override
					public Object processArrayValue(Object arg0,
							JsonConfig arg1)
					{
						return null;
					}
				});
		jsonCfg.registerJsonValueProcessor(BigDecimal.class,
				new JsonValueProcessor()
				{
					@Override
					public Object processObjectValue(String arg0,
							Object arg1, JsonConfig arg2)
					{
						//数值格式化
						if(arg1==null){
							return 0d;
						}else{
							return ((BigDecimal)arg1).doubleValue();
						}
					}

					@Override
					public Object processArrayValue(Object arg0,
							JsonConfig arg1)
					{
						return null;
					}
				});
		return JSONObject.fromObject(obj, jsonCfg);
	}
	
	/**
	 * @category json转成实体 
	 * 2014年9月10日 上午11:30:52 
	 * @author 朱晓陈
	 * @param jsonObject
	 * @param clazz
	 * @return
	 */
	public static Object toBean(JSONObject jsonObject,Class<?> clazz){
		String Format[] = new String[]{"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd HH:mm","yyyy-MM-dd"};
		//这里构造注册一个Morpher转换时间
		JSONUtils.getMorpherRegistry().registerMorpher(new TimestampMorpher(Format)); 
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(Format)); 
		return JSONObject.toBean(jsonObject, clazz);
	}
	
	public static void main(String[] args) {
//		BomBomReq s = new BomBomReq();
////		versionEffDate
//		JSONObject j = new JSONObject();
//		j.put("versionEffDate", "2016-01-01 12:01");
//		s = (BomBomReq) toBean(j, BomBomReq.class);
//		System.out.println(s);
	}
}
