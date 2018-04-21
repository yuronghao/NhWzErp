package com.emi.sys.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

import com.emi.sys.init.Configuration;

public class SysPropertites {
	private static Configuration sys_cfg = null;
	private static Configuration wms_cfg = null;
	public static String get(String key){
//		Configuration cfg = new Configuration("text.properties");
		if(sys_cfg == null){
			sys_cfg = new Configuration("text.properties");
		}
		String value = sys_cfg.getValue(key);
		return value;
	}
	public static String getWmsCfg(String key){
		if(wms_cfg == null){
			wms_cfg = new Configuration("cfg-wms.properties");
		}
		String value = wms_cfg.getValue(key);
		return value;
	}
	
}
