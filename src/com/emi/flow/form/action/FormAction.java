package com.emi.flow.form.action;

import java.io.IOException;

import com.emi.common.action.BaseAction;
import com.emi.common.util.CommonUtil;
import com.emi.flow.form.bean.FLOW_Form;
import com.emi.flow.form.service.FormService;
import com.emi.sys.core.bean.PageBean;
import com.emi.sys.init.Config;

public class FormAction extends BaseAction{
	private FormService formService;
	private FLOW_Form form;

	public void setFormService(FormService formService) {
		this.formService = formService;
	}
	
	public FLOW_Form getForm() {
		return form;
	}

	public void setForm(FLOW_Form form) {
		this.form = form;
	}
	
	/**
	 * @category 跳转到表单列表
	 * 2015年5月15日 下午3:07:31 
	 * @author 朱晓陈
	 * @return
	 */
	public String toFormList(){
		try {
			int pageIndex = getPageIndex();								//页码，从1开始
			int pageSize = Config.PAGESIZE_WEB;							//每页总条数
			
			String condition = "";
			PageBean data = formService.getFormList(pageIndex, pageSize, condition);
			setRequstAttribute("data", data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "form_list";
	}
	
	/**
	 * @category 跳转到表单设计界面
	 * 2015年5月15日 下午1:37:53 
	 * @author 朱晓陈
	 * @return
	 */
	public String toFormDesign(){
		try {
			String formId = getParameter("formId");
			if(!CommonUtil.isNullString(formId)){
				/*----------------------------功能权限控制 begin--------------------------------------*/
				boolean funFlag = checkFunRight("fun_wf_form_02", "flow_0104");//校验权限
				if(!funFlag){return "forbide";} 			//有返回页面的action调用
				/*----------------------------功能权限控制 end----------------------------------------*/
				//查询表单
				FLOW_Form form = formService.findForm(formId);
				setRequstAttribute("form", form);
			}else{
				/*----------------------------功能权限控制 begin--------------------------------------*/
				boolean funFlag = checkFunRight("fun_wf_form_01", "flow_0104");//校验权限
				if(!funFlag){return "forbide";} 			//有返回页面的action调用
				/*----------------------------功能权限控制 end----------------------------------------*/
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "form_design";
	}
	
	/**
	 * @category 保存表单
	 * 2015年5月15日 下午2:57:20 
	 * @author 朱晓陈
	 * @return
	 */
	public String saveFormDesign(){
		try {
			formService.saveForm(form);
			return toFormList();
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	public String deleteForm(){
		/*----------------------------功能权限控制 begin--------------------------------------*/
		boolean funFlag = checkFunRight("fun_wf_form_03", "flow_0104");//校验权限
		if(!funFlag){return "forbide";} 			//有返回页面的action调用
		/*----------------------------功能权限控制 end----------------------------------------*/
		try {
			String formId = getParameter("formId");
			formService.deleteForm(formId);
			return toFormList();
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		
	}
}
