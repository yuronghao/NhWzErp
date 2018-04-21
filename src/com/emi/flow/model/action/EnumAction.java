package com.emi.flow.model.action;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import com.emi.common.action.BaseAction;
import com.emi.flow.main.util.ElementType;
import com.emi.flow.model.bean.FLOW_EnumDetail;
import com.emi.flow.model.service.EnumService;
import com.emi.sys.core.format.EmiJsonArray;

public class EnumAction extends BaseAction{
	private EnumService enumService;

	public void setEnumService(EnumService enumService) {
		this.enumService = enumService;
	}
	
	/**
	 * @category 枚举值
	 * 2015年1月21日 上午9:02:07 
	 * @author 朱晓陈
	 */
	public void getEnumValues(){
		try {
			String enumId = this.getParameter("enumId");
			String elementName = this.getParameter("elementName");
			String value = this.getParameter("value");
			String elType = this.getParameter("elType");
			String readonly = this.getParameter("readonly");
			
			//枚举值
			List<FLOW_EnumDetail> enumList = new ArrayList<FLOW_EnumDetail>();
			enumList = enumService.getEnumValues(enumId);
			//转成json输出
			JSONObject json = new JSONObject();
			json.put("elementName", elementName);
			json.put("value", value);
			json.put("elType", elType);
			json.put("readonly", "true".equals(readonly));
			json.put("enumList", EmiJsonArray.fromObject(enumList));
			getResponse().getWriter().write(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
