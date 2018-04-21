package com.emi.android.action;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONObject;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.emi.android.bean.LoginDetailRsp;
import com.emi.common.action.BaseAction;
import com.emi.common.util.AnalysisApk;
import com.emi.common.util.CommonUtil;
import com.emi.sys.core.format.EmiJsonObj;
import com.emi.wms.basedata.service.BasicSettingService;
import com.emi.wms.bean.Constant;



public class LoginAction extends BaseAction{

	private static final long serialVersionUID = 5471133323739070505L;
	private BasicSettingService basicSettingService;
	
	public void setBasicSettingService(BasicSettingService basicSettingService) {
		this.basicSettingService = basicSettingService;
	}

	/**
	 * @category 安卓登录
	 *2016 2016年4月7日下午1:28:57
	 *String
	 *宋银海
	 */
	
	
	public void login(){
		try {
			JSONObject jsonObj = this.getJsonObject();
			String userCode = jsonObj.getString("userCode");
			String passWord = jsonObj.getString("passWord");
			LoginDetailRsp loginDetailRsp=basicSettingService.androidLogin(userCode, passWord);
			getResponse().getWriter().write(EmiJsonObj.fromObject(loginDetailRsp).toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * @category 检查更新
	 * 2016年8月10日 下午4:56:15 
	 * @author zhuxiaochen
	 */
	public void appUpdate() {
		try{
			
		Map reqmap=new HashMap();
		String path=getRequest().getSession().getServletContext().getRealPath("/");
		//JSONObject jsonObj = getJsonObject();
	    //String version = jsonObj.getString("version");
		SAXReader reader = new SAXReader();
	    Document document = reader.read(new File(path+"/app/version.xml"));
	    
		Element element=document.getRootElement();
		//Iterator ver = element.elementIterator("version");
		//Element verel=(Element) ver.next();
		Iterator url = element.elementIterator("url");
		Element urlel=(Element) url.next();
		
		String[] results = AnalysisApk.unZip(path+urlel.getText(), CommonUtil.getFileRootPath()+ "icon.png");
		//Double baseversion=Double.valueOf(verel.getText());	
		//Double appversion=Double.valueOf(version);	
		//reqmap.put("update", "0");
		//if(appversion<baseversion){
		//reqmap.put("update", "1");
		reqmap.put("version",results[2]);
		reqmap.put("url",urlel.getText());
		//}
		reqmap.put("success", "1");
//		System.out.println(JSONObject.fromObject(reqmap).toString());
		getResponse().getWriter().write(JSONObject.fromObject(reqmap).toString());

		}catch(Exception e){
			e.printStackTrace();
			this.writeError();	
		}
	}

	//
	public void test(){
		try {
			getResponse().getWriter().write("我的ddd");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

