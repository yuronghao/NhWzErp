package com.emi.android.action;

import java.util.ArrayList;
import java.util.List;

import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;

import net.sf.json.JSONObject;

import com.emi.common.action.BaseAction;
import com.emi.sys.core.format.EmiJsonObj;
import com.emi.wms.servicedata.service.PrintServiceEmi;



public class PrintActionEmi extends BaseAction{

	private static final long serialVersionUID = 5471133323739070505L;
	
	private PrintServiceEmi printServiceEmi;
	
	public void setPrintServiceEmi(PrintServiceEmi printServiceEmi) {
		this.printServiceEmi = printServiceEmi;
	}

	//平板获取打印机列表
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
	
	//平板获取打印模板列表
	public void getAllTemplate(){
		try{
			List<String> retList=printServiceEmi.getAllTemplate();
			JSONObject jobj=new JSONObject();
			jobj.put("success", 1);
			jobj.put("failInfor", "");
			jobj.put("data", retList);
//			System.out.println(EmiJsonObj.fromObject(jobj).toString());
		   getResponse().getWriter().write(EmiJsonObj.fromObject(jobj).toString());
		}catch(Exception e){
			e.printStackTrace();
			this.writeErrorOrSuccess(0,"服务错误");
		}
	}
	

	
}

