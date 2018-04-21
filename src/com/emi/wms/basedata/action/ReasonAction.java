package com.emi.wms.basedata.action;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONArray;

import com.emi.common.action.BaseAction;
import com.emi.common.util.CommonUtil;
import com.emi.common.util.DateUtil;
import com.emi.rm.bean.RM_Role;
import com.emi.sys.core.bean.PageBean;
import com.emi.sys.core.format.EmiJsonArray;
import com.emi.sys.init.Config;
import com.emi.wms.basedata.service.ReasonService;
import com.emi.wms.bean.AaDepartment;
import com.emi.wms.bean.AaProviderCustomer;
import com.emi.wms.bean.AaProviderCustomerAddbook;
import com.emi.wms.bean.AaReason;
import com.emi.wms.processDesign.service.BasePDService;

public class ReasonAction extends BaseAction {
private ReasonService reasonService;
private BasePDService basePDService;

	public BasePDService getBasePDService() {
	return basePDService;
}

public void setBasePDService(BasePDService basePDService) {
	this.basePDService = basePDService;
}

	public ReasonService getReasonService() {
	return reasonService;
}

public void setReasonService(ReasonService reasonService) {
	this.reasonService = reasonService;
}

	/**
	 * 
	 * @category 列表
	 * 2015年3月22日 上午8:17:57
	 * @author 杨峥铖
	 * @return
	 */
		public String getreasonList(){
			try {
				int pageIndex = getPageIndex();								//页码，从1开始
				int pageSize = getPageSize();
				String keyWord = getParameter("keyWord");//搜索关键字
				String condition = CommonUtil.combQuerySql("Reason.reasoncode,Reason.reasonname", keyWord);
				setRequstAttribute("keyWord",keyWord);
				PageBean reasonlist = reasonService.getreasonList(pageIndex, pageSize,condition);
				setRequstAttribute("data", reasonlist);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "reasonList";
		}
		
			/**
			 * 
			 * @category 添加页面
			 * 2016年5月6日 下午2:09:26
			 * @author 杨峥铖
			 * @return
			 */
			public String toAddreason(){
				return "reasonAdd";
				}
			
			/**
			 * 
			 * @category 修改页面
			 * 2016年5月6日 下午2:09:40
			 * @author 杨峥铖
			 * @return
			 */
			public String toUpdatereason(){
				AaReason aareason = reasonService.findreason(getParameter("reasonId"));
				setRequstAttribute("reason", aareason);
				return "reasonEdit";
			}
			
			/**
			 * 
			 * @category 添加
			 * 2016年5月6日 下午2:10:20
			 * @author 杨峥铖
			 */
			public void addreason(){
				try {
					AaReason aareason = new AaReason();
					aareason.setReasoncode(getParameter("reasonCode"));
					aareason.setReasonname(getParameter("reasonName"));
					aareason.setIsDelete(0);
					boolean suc = reasonService.addreason(aareason);
					if(suc){
						getResponse().getWriter().write("success");
					}else{
						getResponse().getWriter().write("error");
					}
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		
		/**
		 * 
		 * @category 修改
		 * 2015年3月22日 上午8:18:58
		 * @author 杨峥铖
		 */
			public void updatereason(){
				try {
					String msg = "";
					boolean pass = true;
					if(pass){
						AaReason aareason = new AaReason();
						aareason.setGid(getParameter("reasonId"));
						aareason.setReasoncode(getParameter("reasonCode"));
						aareason.setReasonname(getParameter("reasonName"));
						boolean suc = reasonService.updatereason(aareason);
						if(suc){
							getResponse().getWriter().write("success");
						}else{
							getResponse().getWriter().write("保存失败");
						}
					}else{
						getResponse().getWriter().write(msg);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		
		/**
		 * 
		 * @category 删除
		 * 2015年12月10日 下午5:19:10
		 * @author 杨峥铖
		 */
			public void deletereason(){
				try {
					String gid = getParameter("reasonId");
					reasonService.deletereason(gid);
					responseWrite("success");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
}
