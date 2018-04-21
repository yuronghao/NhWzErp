package com.emi.wms.basedata.action;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Request;

import org.apache.commons.lang.ArrayUtils;

import net.sf.json.JSONArray;

import com.emi.android.bean.DepartmentRsp;
import com.emi.cache.service.CacheCtrlService;
import com.emi.common.action.BaseAction;
import com.emi.common.util.CommonUtil;
import com.emi.common.util.DateUtil;
import com.emi.rm.bean.RM_Role;
import com.emi.sys.core.bean.PageBean;
import com.emi.sys.core.format.EmiJsonArray;
import com.emi.sys.init.Config;
import com.emi.wms.basedata.service.CostItemService;
import com.emi.wms.bean.AAPriorAttribute;
import com.emi.wms.bean.AaDepartment;
import com.emi.wms.bean.AaGoods;
import com.emi.wms.bean.AaProviderCustomer;
import com.emi.wms.bean.AaProviderCustomerAddbook;
import com.emi.wms.bean.MESWMCostItem;
import com.emi.wms.bean.MESWMCostItemAllotRate;
import com.emi.wms.bean.MESWMCostItemSourceSet;
import com.emi.wms.bean.YmRdStyle;
import com.emi.wms.processDesign.service.BasePDService;
import com.opensymphony.xwork2.ActionContext;

public class CostItemAction extends BaseAction {
private CostItemService costItemService;
private BasePDService basePDService;
private File file;

public File getFile() {
	return file;
}

public void setFile(File file) {
	this.file = file;
}


	public BasePDService getBasePDService() {
	return basePDService;
}

public void setBasePDService(BasePDService basePDService) {
	this.basePDService = basePDService;
}

	public CostItemService getCostItemService() {
	return costItemService;
}

public void setCostItemService(CostItemService costItemService) {
	this.costItemService = costItemService;
}




	/**
	 * 
	 * @category 列表
	 * 2015年3月22日 上午8:17:57
	 * @author 杨峥铖
	 * @return
	 */
		public String getcostItemList(){
			try {
				int pageIndex = getPageIndex();								//页码，从1开始
				int pageSize = getPageSize();
				String keyWord = getParameter("keyWord");//搜索关键字
				String condition = CommonUtil.combQuerySql("CostItem.code,CostItem.name", keyWord);
				setRequstAttribute("keyWord",keyWord);
				PageBean costItemlist = costItemService.getcostItemList(pageIndex, pageSize,condition);
				setRequstAttribute("data", costItemlist);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "costItemList";
		}
		
			/**
			 * 
			 * @category 添加页面
			 * 2016年5月6日 下午2:09:26
			 * @author 杨峥铖
			 * @return
			 */
			public String toAddcostItem(){
				return "costItemAdd";
				}
			
			/**
			 * 
			 * @category 修改页面
			 * 2016年5月6日 下午2:09:40
			 * @author 杨峥铖
			 * @return
			 */
			public String toUpdatecostItem(){
				MESWMCostItem aacostItem = costItemService.findcostItem(getParameter("costItemId"));
				AAPriorAttribute prior = costItemService.findprior(aacostItem.getSourceGid());
				AAPriorAttribute priors = costItemService.findprior(aacostItem.getAllotRateGid());
				setRequstAttribute("costItem", aacostItem);
				setRequstAttribute("prior", prior);
				setRequstAttribute("priors", priors);
				return "costItemEdit";
			}
			
			/**
			 * 
			 * @category 添加
			 * 2016年5月6日 下午2:10:20
			 * @author 杨峥铖
			 */
			public void addcostItem(){
				
				try {
					Map session=ActionContext.getContext().getSession();
					MESWMCostItem aacostItem = new MESWMCostItem();
					if(getParameter("costItemCode").equals("")){
						getResponse().getWriter().write("costItemCode");
						return;
					}else {
						aacostItem.setCode(getParameter("costItemCode"));
					}
					if(getParameter("costItemName").equals("")){
						getResponse().getWriter().write("costItemName");
						return;
					}else {
						aacostItem.setName(getParameter("costItemName"));
					}
					if(getParameter("priorattributeGid").equals("")){
						getResponse().getWriter().write("priorattributeGid");
						return;
					}else {
						aacostItem.setSourceGid(getParameter("priorattributeGid"));
					}
					if(getParameter("allotRateGid").equals("")){
						getResponse().getWriter().write("allotRateGid");
						return;
					}else {
						aacostItem.setAllotRateGid(getParameter("allotRateGid"));
					}
					aacostItem.setNotes(getParameter("note"));
					aacostItem.setOrgGid(session.get("OrgId").toString());
					aacostItem.setSobGid(session.get("SobId").toString());
					boolean suc = costItemService.addcostItem(aacostItem);
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
			public void updatecostItem(){
				try {
					String msg = "";
					boolean pass = true;
					if(pass){
						Map session=ActionContext.getContext().getSession();
						MESWMCostItem aacostItem = new MESWMCostItem();
						aacostItem.setGid(getParameter("costItemId"));
						if(getParameter("costItemCode").equals("")){
							getResponse().getWriter().write("costItemCode");
							return;
						}else {
							aacostItem.setCode(getParameter("costItemCode"));
						}
						if(getParameter("costItemName").equals("")){
							getResponse().getWriter().write("costItemName");
							return;
						}else {
							aacostItem.setName(getParameter("costItemName"));
						}
						if(getParameter("priorattributeGid").equals("")){
							getResponse().getWriter().write("priorattributeGid");
							return;
						}else {
							aacostItem.setSourceGid(getParameter("priorattributeGid"));
						}
						if(getParameter("allotRateGid").equals("")){
							getResponse().getWriter().write("allotRateGid");
							return;
						}else {
							aacostItem.setAllotRateGid(getParameter("allotRateGid"));
						}
						aacostItem.setNotes(getParameter("note"));
						aacostItem.setOrgGid(session.get("OrgId").toString());
						aacostItem.setSobGid(session.get("SobId").toString());
						boolean suc = costItemService.updatecostItem(aacostItem);
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
			public void deletecostItem(){
				try {
					String gid = getParameter("costItemId");
					costItemService.deletecostItem(gid);
					responseWrite("success");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			
			/**
			 * 
			* @Title: 成本项目分配率 
			* @Description: TODO(这里用一句话描述这个方法的作用) 
			* @return  设定文件 
			* @return String 返回类型 
			* @throws
			 */
			public String costItemAllotRateList(){
				try {
					int pageIndex = getPageIndex();								//页码，从1开始
					int pageSize = getPageSize();
					String keyWord = getParameter("keyWord");//搜索关键字
					String condition = CommonUtil.combQuerySql("c.name", keyWord);
					setRequstAttribute("keyWord",keyWord);
					PageBean costItemAllotRateList = costItemService.costItemAllotRateList(pageIndex, pageSize,condition);
					setRequstAttribute("data", costItemAllotRateList);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return "costItemAllotRateList";
			}
			
			/**
			 * 
			* @Title: 添加成本项目分配率
			* @Description: TODO(这里用一句话描述这个方法的作用) 
			* @return  设定文件 
			 */
			public String toAddcostItemAllotRate(){
				List<MESWMCostItem> costItems=costItemService.selectcostItems();
				setRequstAttribute("costItems", costItems);
				return "costItemAllotRateAdd";
				}
			
			
			
			public String toImportCostRatePage(){
				return "toImportCostRatePage";
			}
			
			
			//导入成本分配率
			public void importRoute(){ 
				try {
					if(file!=null){
						
						
						Map session=ActionContext.getContext().getSession();
						costItemService.importRoute(file,session.get("OrgId").toString(),session.get("SobId").toString());
						
						responseWrite("success",true);
					}else{
						responseWrite("empty",true);
					}
				} catch (Exception e) {
					e.printStackTrace();
					responseWrite("error",true);
				}
			}
			
			/**
			 * 
			* @Title: 添加成本分配率 
			* @author zxl 2017年6月7日 下午4:24:51
			* @return void
			 */
				public void addcostItemAllotRate(){
					String type="";
				try {
					Map session=ActionContext.getContext().getSession();
					MESWMCostItemAllotRate costItemAllotRate = new MESWMCostItemAllotRate();
					if(getParameter("costItemCode").equals("")){
						getResponse().getWriter().write("costItemCode");
						return;
					}else {
						costItemAllotRate.setCostItemGid(getParameter("costItemCode"));
					}
					if(getParameter("goodsId").equals("")){
						getResponse().getWriter().write("goodsId");
						return;
					}else {
						costItemAllotRate.setGoodsGid(getParameter("goodsId"));
					}
					if(getParameter("priorattributeName").equals("")){
						getResponse().getWriter().write("priorattributeName");
						return;
					}else {
						costItemAllotRate.setCfree1(getParameter("priorattributeName"));
					}
					if(getParameter("depGid").equals("")){
						getResponse().getWriter().write("depGid");
						return;
					}else {
						costItemAllotRate.setDepGid(getParameter("depGid"));
					}
					if(getParameter("ratio").equals("")){
						getResponse().getWriter().write("ratio");
						return;
					}else {
						type="ratios";
						costItemAllotRate.setRatio(new BigDecimal(getParameter("ratio")));
					}
					costItemAllotRate.setNotes(getParameter("note"));
					costItemAllotRate.setOrgGid(session.get("OrgId").toString());
					costItemAllotRate.setSobGid(session.get("SobId").toString());
//					MESWMCostItemAllotRate mRate=costItemService.selectcostItem(getParameter("goodsId"),getParameter("priorattributeName"));
//					if(mRate!=null){
//						getResponse().getWriter().write("noNull");
//						return;
//					}
					boolean suc = costItemService.addcostItemAllotRate(costItemAllotRate);
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
				* @Title: 修改成本项目分配率
				* @author zxl 2017年6月7日 下午5:05:50
				* @return String
				 */
				public String toUpdatecostItemAllotRate(){
					MESWMCostItemAllotRate aacostItem = costItemService.findcostItemAllotRate(getParameter("costItemId"));
					List<MESWMCostItem> costItems=costItemService.selectcostItems();
					setRequstAttribute("costItems", costItems);
					setRequstAttribute("costItem", aacostItem);
					return "costItemAllotRateEdit";
				}
				
			
				public void UpdatecostItemAllotRate(){
					String type="";
				try {
					Map session=ActionContext.getContext().getSession();
					MESWMCostItemAllotRate costItemAllotRate = new MESWMCostItemAllotRate();
					costItemAllotRate.setGid(getParameter("costItemId"));
					if(getParameter("costItemCode").equals("")){
						getResponse().getWriter().write("costItemCode");
						return;
					}else {
						costItemAllotRate.setCostItemGid(getParameter("costItemCode"));
					}
					if(getParameter("goodsId").equals("")){
						getResponse().getWriter().write("goodsId");
						return;
					}else {
						costItemAllotRate.setGoodsGid(getParameter("goodsId"));
					}
					if(getParameter("priorattributeName").equals("")){
						getResponse().getWriter().write("priorattributeName");
						return;
					}else {
						costItemAllotRate.setCfree1(getParameter("priorattributeName"));
					}
					if(getParameter("depGid").equals("")){
						getResponse().getWriter().write("depGid");
						return;
					}else {
						costItemAllotRate.setDepGid(getParameter("depGid"));
					}
					if(getParameter("ratio").equals("")){
						getResponse().getWriter().write("ratio");
						return;
					}else {
						type="ratios";
						costItemAllotRate.setRatio(new BigDecimal(getParameter("ratio")));
					}
					costItemAllotRate.setNotes(getParameter("note"));
					costItemAllotRate.setOrgGid(session.get("OrgId").toString());
					costItemAllotRate.setSobGid(session.get("SobId").toString());
					String suc = costItemService.UpdatecostItemAllotRate(costItemAllotRate);
					if(suc=="Null"){
						getResponse().getWriter().write("success");
					}else if (suc=="noNull"){
						getResponse().getWriter().write("noNull");
					}else {
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
			* @Title: 删除成本项目分配率 
			* @author zxl 2017年6月7日 上午9:06:29
			* @return void
			 */
			public void deletecostItemAllotRate(){
				try {
					String gid = getParameter("costItemAllotRateId");
					costItemService.deletecostItemAllotRate(gid);
					responseWrite("success");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			/**
			 * 
			* @Title: 成本项目取数设置列表 
			* @author zxl 2017年6月7日 上午9:33:57
			* @return String
			 */
			public String costItemSourceSetList(){
				try {
					int pageIndex = getPageIndex();								//页码，从1开始
					int pageSize = getPageSize();
					String keyWord = getParameter("keyWord");//搜索关键字
					String condition = CommonUtil.combQuerySql("c.name", keyWord);
					setRequstAttribute("keyWord",keyWord);
					PageBean costItemSourceSetList = costItemService.costItemSourceSetList(pageIndex, pageSize,condition);
					setRequstAttribute("data", costItemSourceSetList);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return "costItemSourceSetList";
			}
			
			/**
			 * 
			* @Title: 添加成本取数界面 
			* @author zxl 2017年6月8日 上午8:33:46
			* @return String
			 */
			public String toAddcostItemSourceSet(){
				List<MESWMCostItem> costItems=costItemService.selectcostItems();
				List<YmRdStyle> rdStyles=costItemService.selectRdStyles();
				List<AAPriorAttribute> pAttributes=costItemService.selectPriorAttribute();
				setRequstAttribute("costItems", costItems);
				setRequstAttribute("rdStyles", rdStyles);
				setRequstAttribute("pAttributes", pAttributes);
				return "costItemSourceSetAdd";
				}
			
			/**
			 * 
			* @Title: 添加 成本取数
			* @author zxl 2017年6月8日 上午8:46:17
			* @return void
			 */
			public void addcostItemSourceSet(){
				String type="";
			try {
				Map session=ActionContext.getContext().getSession();
				MESWMCostItemSourceSet costItemSourceSet = new MESWMCostItemSourceSet();
				if(getParameter("costItemCode").equals("")){
					getResponse().getWriter().write("costItemCode");
					return;
				}else {
					costItemSourceSet.setCostItemGid(getParameter("costItemCode"));
				}
				
				if(getParameter("sourceMode").equals("")){    										//来源方式
					getResponse().getWriter().write("sourceMode");
					return;
				}else {
					if(getParameter("sourceMode").equals("C5FAD155-A366-4E8D-AAEC-B23282A02BF1")){ //总科目
						if(!getParameter("rdStyleGid").equals("")){  //类别
							getResponse().getWriter().write("rdStyleGids");
							return;
						}else {
							if(getParameter("subjectCode").equals("")){  //科目编码
								getResponse().getWriter().write("subjectCode");
								return;
							}else {
								costItemSourceSet.setSourceMode(getParameter("sourceMode")); 	//总科目
								costItemSourceSet.setSubjectCode(getParameter("subjectCode"));  //科目编码
							}
						}
					}else {
						if(!getParameter("subjectCode").equals("")){  						//科目编码
							getResponse().getWriter().write("subjectCodes");
							return;
						}else {
							costItemSourceSet.setRdStyleGid(getParameter("rdStyleGid"));    //出库类别
							costItemSourceSet.setSourceMode(getParameter("sourceMode")); 	//非总科目
						}
					}
				}
				costItemSourceSet.setDepGid(getParameter("depGid"));
				costItemSourceSet.setNotes(getParameter("note"));
				costItemSourceSet.setOrgGid(session.get("OrgId").toString());
				costItemSourceSet.setSobGid(session.get("SobId").toString());
				boolean suc = costItemService.addcostItemSourceSet(costItemSourceSet);
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
			* @Title: 修改成本取数
			* @author zxl 2017年6月8日 上午9:06:27
			* @return String
			 */
			public String toUpdatecostItemSourceSet(){
				MESWMCostItemSourceSet aacostItem = costItemService.findcostItemSourceSet(getParameter("costItemId"));
				List<AAPriorAttribute> pAttributes=costItemService.selectPriorAttribute();
				List<MESWMCostItem> costItems=costItemService.selectcostItems();
				setRequstAttribute("costItems", costItems);
				setRequstAttribute("costItem", aacostItem);
				setRequstAttribute("pAttributes", pAttributes);
				return "costItemSourceSetEdit";
			}
			
			/**
			 * 
			* @Title: 修改成本取数 
			* @author zxl 2017年6月8日 上午9:19:41
			* @return void
			 */
			public void updatecostItemSourceSet(){
				String type="";
			try {
				Map session=ActionContext.getContext().getSession();
				MESWMCostItemSourceSet costItemSourceSet = new MESWMCostItemSourceSet();
				costItemSourceSet.setGid(getParameter("costItemId"));
				if(getParameter("costItemCode").equals("")){
					getResponse().getWriter().write("costItemCode");
					return;
				}else {
					costItemSourceSet.setCostItemGid(getParameter("costItemCode"));
				}
				if(getParameter("sourceMode").equals("")){    										//来源方式
					getResponse().getWriter().write("sourceMode");
					return;
				}else {
					if(getParameter("sourceMode").equals("C5FAD155-A366-4E8D-AAEC-B23282A02BF1")){ //总科目
						if(!getParameter("rdStyleGid").equals("")){  //类别
							getResponse().getWriter().write("rdStyleGids");
							return;
						}else {
							if(getParameter("subjectCode").equals("")){  //科目编码
								getResponse().getWriter().write("subjectCode");
								return;
							}else {
								costItemSourceSet.setSourceMode(getParameter("sourceMode")); 	//总科目
								costItemSourceSet.setSubjectCode(getParameter("subjectCode"));  //科目编码
								costItemSourceSet.setRdStyleGid(getParameter("rdStyleGid"));
							}
						}
					}else {
						if(!getParameter("subjectCode").equals("")){  						//科目编码
							getResponse().getWriter().write("subjectCodes");
							return;
						}else {
							costItemSourceSet.setRdStyleGid(getParameter("rdStyleGid"));    //出库类别
							costItemSourceSet.setSourceMode(getParameter("sourceMode")); 	//非总科目
							costItemSourceSet.setSubjectCode(getParameter("subjectCode"));
						}
					}
				}
				costItemSourceSet.setDepGid(getParameter("depGid"));
				costItemSourceSet.setNotes(getParameter("note"));
				costItemSourceSet.setOrgGid(session.get("OrgId").toString());
				costItemSourceSet.setSobGid(session.get("SobId").toString());
				boolean suc = costItemService.updatecostItemSourceSet(costItemSourceSet);
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
			* @Title: 删除成本项目
			* @author Administrator 2017年6月7日 上午9:34:46
			* @return void
			 */
			public void deletecostItemSourceSet(){
				try {
					String gid = getParameter("costItemSourceSet");
					costItemService.deletecostItemSourceSet(gid);
					responseWrite("success");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			
}
