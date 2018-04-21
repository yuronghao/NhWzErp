package com.emi.android.action;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.emi.android.bean.DepartmentPersonRsp;
import com.emi.android.bean.DepartmentRsp;
import com.emi.android.bean.ProcessStartScanRsp;
import com.emi.common.action.BaseAction;
import com.emi.sys.core.format.EmiJsonArray;
import com.emi.sys.core.format.EmiJsonObj;
import com.emi.wms.basedata.service.BasicSettingService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;



@SuppressWarnings({"rawtypes","unchecked"})
public class BaseDataAction extends BaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4290709737197755661L;
	private BasicSettingService basicSettingService;
	
	public void setBasicSettingService(BasicSettingService basicSettingService) {
		this.basicSettingService = basicSettingService;
	}



	/**
	 * @category 获得末级部门
	 *2016 2016年6月14日上午10:48:04
	 *void
	 *宋银海
	 */
	public void getDepartmentLast(){
		
		try {

			JSONObject json = getJsonObject();
			String orgId=json.getString("orgId");
			String sobId=json.getString("sobId");
			String billType=json.getString("billType");
			
			List<DepartmentRsp> drs=basicSettingService.getDepartmentLast(orgId,sobId,billType);
//			System.out.println(EmiJsonArray.fromObject(drs).toString());
		    getResponse().getWriter().write(EmiJsonArray.fromObject(drs).toString());
		    
		} catch (Exception e){
			e.printStackTrace();
			this.writeError();
		}
		
	}
	
	/////////////////////reerwewew
	
	
	/**
	 * @category 获得末级部门下的人员
	 *2016 2016年6月14日上午10:48:04
	 *void
	 *宋银海
	 */
	public void getDepartmentPersonList(){
		
		try {
			JSONObject json = getJsonObject();
			String orgId=json.getString("orgId");
			String sobId=json.getString("sobId");
			String dptGid=json.getString("dptGid");
			
			List<DepartmentPersonRsp> dpr=basicSettingService.getDepartmentPersonList(orgId,sobId,dptGid);
//			System.out.println(EmiJsonArray.fromObject(dpr).toString());
		    getResponse().getWriter().write(EmiJsonArray.fromObject(dpr).toString());
		    
		} catch (Exception e){
			e.printStackTrace();
			this.writeError();
		}
	}
	
	
	
	
}

//test56565565777