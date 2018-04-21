package com.emi.wms.basedata.action;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.validator.Valid;

import net.sf.json.JSONArray;
import oracle.net.aso.f;
import ytx.org.apache.http.HttpResponse;

import com.emi.common.action.BaseAction;
import com.emi.common.util.CommonUtil;
import com.emi.common.util.DateUtil;
import com.emi.rm.bean.RM_Role;
import com.emi.sys.core.bean.PageBean;
import com.emi.sys.core.format.EmiJsonArray;
import com.emi.sys.init.Config;
import com.emi.wms.basedata.service.BasicSettingService;
import com.emi.wms.basedata.service.MouldService;
import com.emi.wms.bean.AaDepartment;
import com.emi.wms.bean.AaProviderCustomer;
import com.emi.wms.bean.AaProviderCustomerAddbook;
import com.emi.wms.bean.Classify;
import com.emi.wms.bean.Mould;
import com.emi.wms.processDesign.service.BasePDService;

public class MouldAction extends BaseAction {
private MouldService mouldService;
private BasePDService basePDService;


	public BasePDService getBasePDService() {
	return basePDService;
}

public void setBasePDService(BasePDService basePDService) {
	this.basePDService = basePDService;
}

	public MouldService getmouldService() {
	return mouldService;
}

public void setmouldService(MouldService mouldService) {
	this.mouldService = mouldService;
}




	/**
	 * @category 进入模具页，查询当前右边的信息
	 *2017 2017年5月17日上午8:46:49
	 *String
	 *张向龙
	 */
	public String getRightMould(){
	
		try{
			int pageIndex = getPageIndex();	
			int pageSize = getPageSize();
		
			String classifyGid=getParameter("id");
			String orgId=getSession().get("OrgId").toString();//组织id
			String sobId=getSession().get("SobId").toString();//账套id
		
			String keyWord = getParameter("keyWord");//搜索关键字
			String condition = "";
			if(CommonUtil.notNullString(keyWord)){
				condition = " and (mould.mouldName like '%"+keyWord+"%' or mould.mouldCode like '%"+keyWord+"%') ";
			}
			/*PageBean pageBean=mouldService.getGoodsPageBean(pageIndex,pageSize,classifyGid,orgId,sobId,condition);*/
			PageBean pageBean=mouldService.getmouldListDatils(pageIndex,pageSize,classifyGid,condition);
			setRequstAttribute("id", classifyGid);
			setRequstAttribute("data", pageBean);	
			setRequstAttribute("keyWord", keyWord);
			return "mouldDetails";
		}catch(Exception e){
			e.printStackTrace();
			return "mouldDetails";
		}
		
	}


	/**
	 * @category 进入模具页，查询当前右边的物品
	 *2017年7月18日上午17:35
	 *String
	 *张向龙
	 */
	public String getRightGoods(){
		
		try{
			int pageIndex = getPageIndex();	
			int pageSize = getPageSize();
			
			String classifyGid=getParameter("id");
			String orgId=getSession().get("OrgId").toString();//组织id
			String sobId=getSession().get("SobId").toString();//账套id
			
			String keyWord = getParameter("keyWord");//搜索关键字
			String condition = "";
			if(CommonUtil.notNullString(keyWord)){
				condition = " and (gs.goodscode like '%"+keyWord+"%' or gs.goodsname like '%"+keyWord+"%' or gs.goodsstandard like '%"+keyWord+"%')";
			}
			//PageBean pageBean=basicSettingService.getGoodsPageBean(pageIndex,pageSize,classifyGid,orgId,sobId,condition);
			PageBean pageBean=mouldService.getmouldListDatils(pageIndex,pageSize,classifyGid,condition);
			setRequstAttribute("id", classifyGid);
			setRequstAttribute("data", pageBean);	
			setRequstAttribute("keyWord", keyWord);
			return "mouldDetails";
		}catch(Exception e){
			e.printStackTrace();
			return "mouldDetails";
		}
	}





	/**
	 * 
	 * @category 列表
	 * 2015年3月22日 上午8:17:57
	 * @author 杨峥铖
	 * @return
	 */
		public String getmouldList(){
			try {
				int pageIndex = getPageIndex();								//页码，从1开始
				int pageSize = getPageSize();
				String keyWord = getParameter("keyWord");//搜索关键字
				String condition = CommonUtil.combQuerySql("mould.mouldCode,mould.mouldName", keyWord);
				setRequstAttribute("keyWord",keyWord);
				String orgId=getSession().get("OrgId").toString();
				String sobId=getSession().get("SobId").toString();
				condition +=" and mould.sobGid='"+sobId+"' and mould.orgGid='"+orgId+"'";
				PageBean mouldlist = mouldService.getmouldList(pageIndex, pageSize,condition);
				setRequstAttribute("data", mouldlist);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "mouldList";
		}
		
			/**
			 * 
			 * @category 添加页面
			 * 2016年5月6日 下午2:09:26
			 * @author 杨峥铖
			 * @return
			 */
			public String toAddmould(){
				return "mouldAdd";
				}
			
			/**
			 * 
			 * @category 修改页面
			 * 2016年5月6日 下午2:09:40
			 * @author 杨峥铖
			 * @return
			 */
			public String toUpdatemould(){
				Mould aamould = mouldService.findmould(getParameter("mouldId"));
				//查询客户
				String cutomerGid=getParameter("cutomerGid");
				AaProviderCustomer customer=mouldService.selectCustomer(cutomerGid);
				//查询供应商
				String providerGid=getParameter("providerGid");
				AaProviderCustomer provider=mouldService.selectCurrentDeptGid(providerGid);
				//查询所在部门
				String currentDeptGid=getParameter("currentDeptGid");
				AaDepartment currentDept=mouldService.selectDepartment(currentDeptGid);
				//查询模具分类
				String mouldStyleId=getParameter("mouldStyleId");
				Classify mouldStyle=mouldService.selectMouldStyle(mouldStyleId);
				
				setRequstAttribute("mould", aamould);
				setRequstAttribute("customer", customer);
				setRequstAttribute("provider", provider);
				setRequstAttribute("currentDept", currentDept);
				setRequstAttribute("mouldStyle", mouldStyle);
				return "mouldEdit";
			}
			
			/**
			 * 
			 * @category 添加
			 * 2016年5月6日 下午2:10:20
			 * @author 杨峥铖
			 */
			public void addmould(){
				String type="";
				try {
					Mould aamould = new Mould();
					if(getParameter("mouldStyleId").equals("")){
						getResponse().getWriter().write("mouldStyleId");
						return;
					}else {
						aamould.setMouldstyle(getParameter("mouldStyleId"));
					}
					if(getParameter("mouldCode").equals("")){
						getResponse().getWriter().write("mouldCode");
						return;
					}else {
						aamould.setMouldcode(getParameter("mouldCode"));
					}
					if(getParameter("mouldName").equals("")){
						getResponse().getWriter().write("mouldName");
						return;
					}else {
						aamould.setMouldname(getParameter("mouldName"));
					}
					if(!getParameter("barcode").equals("")){
						String barcode=getParameter("barcode");
						String fistBarcode=barcode.substring(0, 1);
						if(fistBarcode.equals("M")){
							aamould.setBarcode(getParameter("barcode"));
						}else if (fistBarcode.equals("m")) {
							getResponse().getWriter().write("mbarcode");
							return;
						}else {
							getResponse().getWriter().write("erroBarcode");
							return;
						}
					}else if(getParameter("barcode").equals("")){
						getResponse().getWriter().write("barcode");
						return;
					}
					
					aamould.setMouldBeginTime(getParameter("mouldBeginTime").length()>0?new Timestamp(DateUtil.stringtoDate(getParameter("mouldBeginTime"), "yyyy-MM-dd").getTime()):null);
					aamould.setTexture(getParameter("texture"));
					aamould.setSize(getParameter("size"));
					type="cost";
					if(!getParameter("cost").equals("")){
						aamould.setCost(new BigDecimal(getParameter("cost")));
					}
					aamould.setProcessingUnit(getParameter("processingUnit"));
					aamould.setMouldstatus(new Integer(getParameter("mouldstatus")));
					aamould.setMouldScrapTime(getParameter("mouldScrapTime").length()>0?new Timestamp(DateUtil.stringtoDate(getParameter("mouldScrapTime"), "yyyy-MM-dd").getTime()):null);
					aamould.setMouldScrapReason(getParameter("mouldScrapReason"));
				
					aamould.setCurrentDeptGid(getParameter("currentDeptGid"));
					aamould.setCdefine1(getParameter("cdefine1"));
					aamould.setPartNumber(getParameter("partNumber"));
					aamould.setPartName(getParameter("partName"));
					type="multimodeOrder";
					if(!getParameter("multimodeOrder").equals("")){
						aamould.setMultimodeOrder(new Integer(getParameter("multimodeOrder")));
					}
					aamould.setMouldRatio(getParameter("mouldRatio"));
					aamould.setCavity(getParameter("cavity"));
					aamould.setDataCode(getParameter("dataCode"));
					aamould.setFileLocation(getParameter("fileLocation"));
					aamould.setPosition(getParameter("position"));
					aamould.setPlacingTooling(getParameter("placingTooling"));
					aamould.setPreTooling(getParameter("preTooling"));
					aamould.setMouldFlow(getParameter("mouldFlow"));
					aamould.setStorageTime(getParameter("storageTime").length()>0?new Timestamp(DateUtil.stringtoDate(getParameter("storageTime"), "yyyy-MM-dd").getTime()):null);
					type="life";
					if(!getParameter("life").equals("")){
						aamould.setLife(new Integer(getParameter("life")));
					}
					type="addlife";
					if(!getParameter("addlife").equals("")){
						aamould.setAddlife(new Integer(getParameter("addlife")));
					}
					type="usedlife";
					if(!getParameter("usedlife").equals("")){
						aamould.setUsedlife(new Integer(getParameter("usedlife")));
					}
					type="canuselife";
					if(!getParameter("canuselife").equals("")){
						aamould.setCanuselife(new Integer(getParameter("canuselife")));
					}
					aamould.setOutTime(getParameter("outTime").length()>0?new Timestamp(DateUtil.stringtoDate(getParameter("outTime"), "yyyy-MM-dd").getTime()):null);
					aamould.setCutomerGid(getParameter("cutomerGid"));
					aamould.setProviderGid(getParameter("providerGid"));
					type="openCost";
					if(!getParameter("openCost").equals("")){
						aamould.setOpenCost(new BigDecimal(getParameter("openCost")));
					}
					aamould.setIsreturn(new Integer(getParameter("isreturn")));
					aamould.setReturnTime(getParameter("returnTime").length()>0?new Timestamp(DateUtil.stringtoDate(getParameter("returnTime"), "yyyy-MM-dd").getTime()):null);
					aamould.setIsShareOrver(new Integer(getParameter("isShareOrver")));
					aamould.setShareOrverTime(getParameter("shareOrverTime").length()>0?new Timestamp(DateUtil.stringtoDate(getParameter("shareOrverTime"), "yyyy-MM-dd").getTime()):null);
					aamould.setNotes(getParameter("notes"));
					aamould.setSobgid(getSession().get("SobId").toString());
					aamould.setOrggid(getSession().get("OrgId").toString());
					/*aamould.setMouldstatus(new Integer(getParameter("mouldstatus")));*/
					aamould.setIsDelete(0);
					
					aamould.setCreateUser(CommonUtil.Obj2String(getSession().get("UserId")));
					aamould.setCreateDate(new Timestamp(new Date().getTime()));
					aamould.setSupplyunit(getParameter("supplyunit"));
					
					boolean suc = mouldService.addmould(aamould);
					if(suc){
						getResponse().getWriter().write("success");
					}else{
						getResponse().getWriter().write("error");
					}
				} catch (NumberFormatException e) {
					try {
						getResponse().getWriter().write(type);
					} catch (IOException e1) {
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					}
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
			public void updatemould(){
				
				String type="";
				try {
					String msg = "";
					boolean pass = true;
					if(pass){
						Mould aamould = new Mould();
						aamould.setGid(getParameter("mouldId"));
						if(getParameter("mouldStyleId").equals("")){
							getResponse().getWriter().write("mouldStyleId");
							return;
						}else {
							aamould.setMouldstyle(getParameter("mouldStyleId"));
						}
						if(getParameter("mouldCode").equals("")){
							getResponse().getWriter().write("mouldCode");
							return;
						}else {
							aamould.setMouldcode(getParameter("mouldCode"));
						}
						if(getParameter("mouldName").equals("")){
							getResponse().getWriter().write("mouldName");
							return;
						}else {
							aamould.setMouldname(getParameter("mouldName"));
						}
						
						if(!getParameter("barcode").equals("")){
							String barcode=getParameter("barcode");
							String fistBarcode=barcode.substring(0, 1);
							if(fistBarcode.equals("M")){
								aamould.setBarcode(getParameter("barcode"));
							}else if (fistBarcode.equals("m")) {
								getResponse().getWriter().write("mbarcode");
								return;
							}else {
								getResponse().getWriter().write("erroBarcode");
								return;
							}
						}else if(getParameter("barcode").equals("")){
							getResponse().getWriter().write("barcode");
							return;
						}
						aamould.setMouldBeginTime(getParameter("mouldBeginTime").length()>0?new Timestamp(DateUtil.stringtoDate(getParameter("mouldBeginTime"), "yyyy-MM-dd").getTime()):null);
						aamould.setTexture(getParameter("texture"));
						aamould.setSize(getParameter("size"));
						type="cost";
						if(!getParameter("cost").equals("")){
							aamould.setCost(new BigDecimal(getParameter("cost")));
						}
						aamould.setProcessingUnit(getParameter("processingUnit"));
						/*aamould.setMouldstatus(new Integer(getParameter("mouldstatus")));*/
						aamould.setMouldScrapTime(getParameter("mouldScrapTime").length()>0?new Timestamp(DateUtil.stringtoDate(getParameter("mouldScrapTime"), "yyyy-MM-dd").getTime()):null);
						aamould.setMouldScrapReason(getParameter("mouldScrapReason"));
						aamould.setCurrentDeptGid(getParameter("currentDeptGid"));
						aamould.setCdefine1(getParameter("cdefine1"));
						aamould.setPartNumber(getParameter("partNumber"));
						aamould.setPartName(getParameter("partName"));
						type="multimodeOrder";
						if(!getParameter("multimodeOrder").equals("")){
							aamould.setMultimodeOrder(new Integer(getParameter("multimodeOrder")));
						}
						aamould.setMouldRatio(getParameter("mouldRatio"));
						aamould.setCavity(getParameter("cavity"));
						aamould.setDataCode(getParameter("dataCode"));
						aamould.setFileLocation(getParameter("fileLocation"));
						aamould.setPosition(getParameter("position"));
						aamould.setPlacingTooling(getParameter("placingTooling"));
						aamould.setPreTooling(getParameter("preTooling"));
						aamould.setMouldFlow(getParameter("mouldFlow"));
						aamould.setStorageTime(getParameter("storageTime").length()>0?new Timestamp(DateUtil.stringtoDate(getParameter("storageTime"), "yyyy-MM-dd").getTime()):null);
						type="life";
						if(!getParameter("life").equals("")){
							aamould.setLife(new Integer(getParameter("life")));
						}
						type="addlife";
//						System.out.println(getParameter("addlife"));
						if(!getParameter("addlife").equals("")){
							aamould.setAddlife(new Integer(getParameter("addlife")));
						}
						type="usedlife";
						if(!getParameter("usedlife").equals("")){
							aamould.setUsedlife(new Integer(getParameter("usedlife")));
						}
						type="canuselife";
						if(!getParameter("canuselife").equals("")){
							aamould.setCanuselife(new Integer(getParameter("canuselife")));
						}
						aamould.setOutTime(getParameter("outTime").length()>0?new Timestamp(DateUtil.stringtoDate(getParameter("outTime"), "yyyy-MM-dd").getTime()):null);
						aamould.setCutomerGid(getParameter("cutomerGid"));
						aamould.setProviderGid(getParameter("providerGid"));
						type="openCost";
						if(!getParameter("openCost").equals("")){
							aamould.setOpenCost(new BigDecimal(getParameter("openCost")));
						}
						aamould.setIsreturn(new Integer(getParameter("isreturn")));
						aamould.setReturnTime(getParameter("returnTime").length()>0?new Timestamp(DateUtil.stringtoDate(getParameter("returnTime"), "yyyy-MM-dd").getTime()):null);
						aamould.setIsShareOrver(new Integer(getParameter("isShareOrver")));
						aamould.setShareOrverTime(getParameter("shareOrverTime").length()>0?new Timestamp(DateUtil.stringtoDate(getParameter("shareOrverTime"), "yyyy-MM-dd").getTime()):null);
						aamould.setNotes(getParameter("notes"));
						aamould.setSobgid(getSession().get("SobId").toString());
						aamould.setOrggid(getSession().get("OrgId").toString());
						aamould.setMouldstatus(new Integer(getParameter("mouldstatus")));
						aamould.setIsDelete(0);
						
						aamould.setModifyUser(CommonUtil.Obj2String(getSession().get("UserId")));
						aamould.setModifyDate(new Timestamp(new Date().getTime()));
						aamould.setSupplyunit(getParameter("supplyunit"));
						
						boolean suc = mouldService.updatemould(aamould);
						if(suc){
							getResponse().getWriter().write("success");
						}else{
							getResponse().getWriter().write("error");
						}
					}else{
						getResponse().getWriter().write(msg);
					}
					
				} catch (Exception e) {
					try {
						getResponse().getWriter().write(type);
					} catch (IOException e1) {
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					}
				}
			}
		
		/**
		 * 
		 * @category 删除
		 * 2015年12月10日 下午5:19:10
		 * @author 杨峥铖
		 */
			public void deletemould(){
				try {
					String gid = getParameter("mouldId");
					mouldService.deletemould(gid);
					responseWrite("success");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			
			/**
			 * 
			* @Title: selectCurrentDept 
			* @Description: 查询所有部门
			* @return void 返回类型 
			*  张向龙
			*  2017年5月17日 下午4:19:10
			 */
			public String selectCurrentDept(){
				
				List classify = mouldService.getClassifyList();
				Map m=new HashMap();
				m.put("id", 0);
				m.put("pId",-1);
				m.put("name", "全部");
				classify.add(m);
				getRequest().setAttribute("classify",JSONArray.fromObject(classify).toString());
				return "mouldTree";
			}
			
}
