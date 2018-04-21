package com.emi.androidweigh.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.emi.androidweigh.service.WeighService;
import com.emi.common.action.BaseAction;
import com.emi.common.util.CommonUtil;
import com.emi.sys.core.bean.PageBean;
import com.emi.sys.core.format.EmiJsonObj;
import com.emi.sys.init.Config;

public class WeighAction extends BaseAction{

	private static final long serialVersionUID = -6694620843707680883L;
	private WeighService weighService;

	public void setWeighService(WeighService weighService) {
		this.weighService = weighService;
	}
	
	
	/**
	 * @category 待称重物料
	 *2016 2016年5月23日上午8:38:18
	 *List<Map>
	 *宋银海
	 */
	public void getToWeighList(){
		
		try {
			JSONObject json = getJsonObject();
			
			List<Map> resultMap=weighService.getToWeighList();
			
			Map map=new HashMap();
			map.put("success", 1);
			map.put("data", resultMap);
			
		    getResponse().getWriter().write(EmiJsonObj.fromObject(map).toString());
		    
		} catch (Exception e){
			e.printStackTrace();
			this.writeError();
		}
		
	}
	
	
	/**
	 * @category 待称重物料详情
	 *2016 2016年5月23日上午8:38:18
	 *List<Map>
	 *宋银海
	 */
	public void getToWeighDetail(){
		
		try {
			JSONObject json = getJsonObject();
			
			String id=json.getString("id");
			List<Map> resultMap=weighService.getToWeighDetail(id);
			
			JSONObject jobj=new JSONObject();
			jobj.put("success", 1);
			jobj.put("failInfor", "");
			jobj.put("data", resultMap);
			System.out.println(EmiJsonObj.fromObject(jobj).toString());
		    getResponse().getWriter().write(EmiJsonObj.fromObject(jobj).toString());
		    
		} catch (Exception e){
			e.printStackTrace();
			this.writeErrorOrSuccess(0,"无任务详情");
		}
		
	}
	
	
	/**
	 * @category 提交称重
	 *2016 2016年5月23日上午8:38:18
	 *List<Map>
	 *宋银海
	 */
	public void subWeigh(){
		
		try {
			JSONArray jsonArray = getJsonArray();
			weighService.subWeigh(jsonArray);
			this.writeErrorOrSuccess(1,"提交成功");
		    
		} catch (Exception e){
			e.printStackTrace();
			this.writeErrorOrSuccess(0,"无任务详情");
		}
		
	}
	
	
	/**
	 * @category 工序领料称重列表
	 *2016 2016年6月27日上午10:08:35
	 *void
	 *宋银海
	 */
	public void getProcessTaskList(){
		try {
			
			JSONObject json = getJsonObject();
			String userGid = json.getString("userGid");                 //用户uid
			String taskTypeCode = json.getString("taskTypeCode");       //单据类型
			List<Map> mapResult=weighService.getProcessTaskList(taskTypeCode);
			
			Map reqmap=new HashMap();
			reqmap.put("success", 1);
			reqmap.put("data",mapResult);
//			System.out.println(EmiJsonObj.fromObject(reqmap).toString());
			getResponse().getWriter().write(EmiJsonObj.fromObject(reqmap).toString());
		} catch (Exception e) {
			e.printStackTrace();
			this.writeError();
		}
		
	}
	
	/**
	 * @category 获取打印列表
	 *2016 2016年7月18日下午2:13:06
	 *void
	 *宋银海
	 */
	public void printList(){
		 try {
		   HashPrintRequestAttributeSet pras=new HashPrintRequestAttributeSet();
		   DocFlavor flavor=DocFlavor.INPUT_STREAM.AUTOSENSE;
		   PrintService[] printService=PrintServiceLookup.lookupPrintServices(flavor, pras);
		   List<String> retList=new ArrayList<String>();
		   if(printService!=null){
			   for(PrintService p:printService){
				   retList.add(p.getName());  
			   }
		   }
			JSONObject jobj=new JSONObject();
			jobj.put("success", 1);
			jobj.put("failInfor", "");
			jobj.put("data", retList);
			 getResponse().getWriter().write(EmiJsonObj.fromObject(jobj).toString());
	 }catch (Exception e){
			e.printStackTrace();
			this.writeErrorOrSuccess(0,"服务错误");
		}

	}
	
	
	/**
	 * @category 提交打印
	 *2016 2016年7月18日下午2:13:06
	 *void
	 *宋银海
	 */
	public void subPrint(){
		try {
			
			JSONObject json = getJsonObject();
			boolean ok=weighService.createFile(json);
			if(ok){
				this.writeSuccess();
			}else{
				this.writeError();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			this.writeError();
		}
		
	}
	
	
	
	
}
