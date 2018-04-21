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
import com.emi.wms.basedata.service.EquipmentService;
import com.emi.wms.bean.AaDepartment;
import com.emi.wms.bean.AaProviderCustomer;
import com.emi.wms.bean.AaProviderCustomerAddbook;
import com.emi.wms.bean.Equipment;
import com.emi.wms.processDesign.service.BasePDService;

public class EquipmentAction extends BaseAction {
private EquipmentService equipmentService;
private BasePDService basePDService;

	public BasePDService getBasePDService() {
	return basePDService;
}

public void setBasePDService(BasePDService basePDService) {
	this.basePDService = basePDService;
}

	public EquipmentService getEquipmentService() {
	return equipmentService;
}

public void setEquipmentService(EquipmentService equipmentService) {
	this.equipmentService = equipmentService;
}

	/**
	 * 
	 * @category 列表
	 * 2015年3月22日 上午8:17:57
	 * @author 杨峥铖
	 * @return
	 */
		public String getequipmentList(){
			try {
				int pageIndex = getPageIndex();								//页码，从1开始
				int pageSize = getPageSize();
				String keyWord = getParameter("keyWord");//搜索关键字
				String condition = CommonUtil.combQuerySql("Equipment.equipmentCode,Equipment.equipmentName", keyWord);
				setRequstAttribute("keyWord",keyWord);
				String orgId=getSession().get("OrgId").toString();
				String sobId=getSession().get("SobId").toString();
				condition +=" and Equipment.sobGid='"+sobId+"' and Equipment.orgGid='"+orgId+"'";
				PageBean equipmentlist = equipmentService.getequipmentList(pageIndex, pageSize,condition);
				setRequstAttribute("data", equipmentlist);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "equipmentList";
		}
		
			/**
			 * 
			 * @category 添加页面
			 * 2016年5月6日 下午2:09:26
			 * @author 杨峥铖
			 * @return
			 */
			public String toAddequipment(){
				return "equipmentAdd";
				}
			
			/**
			 * 
			 * @category 修改页面
			 * 2016年5月6日 下午2:09:40
			 * @author 杨峥铖
			 * @return
			 */
			public String toUpdateequipment(){
				Equipment aaequipment = equipmentService.findequipment(getParameter("equipmentId"));
				AaDepartment department = basePDService.findDepartment(aaequipment.getDepartment());
				setRequstAttribute("department", department);
				setRequstAttribute("equipment", aaequipment);
				return "equipmentEdit";
			}
			
			/**
			 * 
			 * @category 添加
			 * 2016年5月6日 下午2:10:20
			 * @author 杨峥铖
			 */
			public void addequipment(){
				try {
					Equipment aaequipment = new Equipment();
					aaequipment.setEquipmentcode(getParameter("equipmentCode"));
					aaequipment.setEquipmentname(getParameter("equipmentName"));
					aaequipment.setSBL(getParameter("SBL"));
					/*aaequipment.setEquipmentstyle(getParameter("equipmentStyle"));
					aaequipment.setEquipmentspe(getParameter("equipmentSpe"));*/
					aaequipment.setDepartment(getParameter("depUid"));
					//aaequipment.setWorkcenter(getParameter("workCenter"));
					//aaequipment.setPosition(getParameter("123"));
					aaequipment.setSobgid(getSession().get("SobId").toString());
					aaequipment.setOrggid(getSession().get("OrgId").toString());
					aaequipment.setEquipstatus(new Integer(getParameter("equipstatus")));
					aaequipment.setBarcode(getParameter("barcode"));
					aaequipment.setIsDelete(0);
					boolean suc = equipmentService.addequipment(aaequipment);
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
			public void updateequipment(){
				try {
					String msg = "";
					boolean pass = true;
					if(pass){
						Equipment aaequipment = new Equipment();
						aaequipment.setGid(getParameter("equipmentId"));
						aaequipment.setEquipmentcode(getParameter("equipmentCode"));
						aaequipment.setEquipmentname(getParameter("equipmentName"));
						aaequipment.setSBL(getParameter("SBL"));
						/*aaequipment.setEquipmentstyle(getParameter("equipmentStyle"));
						aaequipment.setEquipmentspe(getParameter("equipmentSpe"));*/
						aaequipment.setDepartment(getParameter("depUid"));
						//aaequipment.setWorkcenter(getParameter("workCenter"));
						//aaequipment.setPosition(getParameter("123"));
						aaequipment.setSobgid(getSession().get("SobId").toString());
						aaequipment.setOrggid(getSession().get("OrgId").toString());
						aaequipment.setEquipstatus(new Integer(getParameter("equipstatus")));
						aaequipment.setBarcode(getParameter("barcode"));
						aaequipment.setIsDelete(0);
						boolean suc = equipmentService.updateequipment(aaequipment);
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
			public void deleteequipment(){
				try {
					String gid = getParameter("equipmentId");
					equipmentService.deleteequipment(gid);
					responseWrite("success");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
}
