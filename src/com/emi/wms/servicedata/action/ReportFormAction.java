package com.emi.wms.servicedata.action;

import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;

import net.sf.json.JSONObject;

import com.emi.common.action.BaseAction;
import com.emi.common.util.CommonUtil;
import com.emi.common.util.DateUtil;
import com.emi.common.util.ExcelUtil;
import com.emi.sys.core.bean.PageBean;
import com.emi.wms.servicedata.service.ReportFormService;

public class ReportFormAction extends BaseAction {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6838590625277949046L;

	private ReportFormService reportFormService;
	
	
	public void setReportFormService(ReportFormService reportFormService) {
		this.reportFormService = reportFormService;
	}


	/**
	 * 跳转到计件工资列表
	 *2016 2016年6月16日上午9:18:27
	 *String
	 *宋银海
	 */
	public String getPieceworkSum(){
		try{
			String depName=getParameter("depName");
			String depUid=getParameter("depUid");
			String personGid=getParameter("personGid");
			String personName=getParameter("personName");
			String groupGid=getParameter("groupGid");
			String groupName=getParameter("groupName");
			String startMonth=getParameter("startMonth");
			String endMonth=getParameter("endMonth");
			int pageIndex = getPageIndex();
			int pageSize = getPageSize();
			
			Map params=new HashMap();
			params.put("depUid", depUid);
			params.put("personGid", personGid);
			params.put("groupGid", groupGid);
			params.put("startMonth", startMonth);
			params.put("endMonth", endMonth);
			params.put("pageIndex", pageIndex);
			params.put("pageSize", pageSize);
			
			PageBean pageBean=reportFormService.getPieceworkSum(params);
			setRequstAttribute("data", pageBean);
			setRequstAttribute("depName", depName);
			setRequstAttribute("depUid", depUid);
			setRequstAttribute("personGid", personGid);
			setRequstAttribute("personName", personName);
			setRequstAttribute("startMonth", startMonth);
			setRequstAttribute("endMonth", endMonth);
			setRequstAttribute("groupGid", groupGid);
			setRequstAttribute("groupName", groupName);
			
			return "pieceworkSumList";
		}catch(Exception e){
			e.printStackTrace();
			return "pieceworkSumList";
		}

	}
	
	/**
	 * @category 计件工资详情表
	 * 2016年6月16日 下午2:40:41 
	 * @author zhuxiaochen
	 * @return
	 */
	public String getPieceworkDetail(){
		try {
			int pageIndex = getPageIndex();
			int pageSize = getPageSize();
			String billcode = getParameter("billcode");//查询条件-订单号
			String depName = getParameter("depName");//部门名称
			String deptId = getParameter("depUid");//查询条件-部门id
			String personId = getParameter("personId");//人员id
			String personName =URLDecoder.decode(CommonUtil.Obj2String(getParameter("personName"))  ,"UTF-8");//人员
			String groupId = getParameter("groupId");//班组id
			String groupName = URLDecoder.decode(CommonUtil.Obj2String(getParameter("groupName"))  ,"UTF-8");//班组
			String startDate = getParameter("startDate");//开始时间
			String endDate = getParameter("endDate");//结束时间
			PageBean data = reportFormService.getPieceworkDetail(billcode,deptId,personId,groupId,startDate,endDate,pageIndex,pageSize);
			setRequstAttribute("data", data);
			setRequstAttribute("billcode", billcode);
			setRequstAttribute("depName", depName);
			setRequstAttribute("depUid", deptId);
			setRequstAttribute("personId", personId);
			setRequstAttribute("groupId", groupId);
			setRequstAttribute("startDate", startDate);
			setRequstAttribute("endDate", endDate);
			setRequstAttribute("personName", personName);
			setRequstAttribute("groupName", groupName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "pieceworkDetailList";
	}
	
	/**
	 * @category 导出计件工资详情
	 * 2016年6月16日 下午2:40:41 
	 * @author zhuxiaochen
	 * @return
	 */
	public void exportPieceworkDetail(){
		try {
			String billcode = getParameter("billcode");//查询条件-订单号
			String deptId = getParameter("depUid");//查询条件-部门id
			String personId = getParameter("personId");//人员id
			String personName = getParameter("personName");//人员
			String groupId = getParameter("groupId");//班组id
			String groupName = getParameter("groupName");//班组
			String startDate = getParameter("startDate");//开始时间
			String endDate = getParameter("endDate");//结束时间
			PageBean data = reportFormService.getPieceworkDetail(billcode,deptId,personId,groupId,startDate,endDate,0,0);
			
			String fileName = "计件工资详情表-"+DateUtil.dateToString(new Date(),"yyyy-MM-dd");
			String title = "计件工资详情表";
			String column_str = "{'billCode':'订单编号','goodsCode':'产品编码','goodsName':'产品名称','number':'生产数量','completedNum':'完工数量','opname':'工序名称','opdes':'工序说明','personCode':'人员编码','personName':'人员名称',"
					+ "'groupCode':'班组编码','groupName':'班组名称','disNum':'开工数量','startTime':'开工时间','reportOkNum':'报工数量','endTime':'报工时间','realPrice':'工序单价','totalPrice':'合计金额'}";
			exportExcel(column_str, data.getList(), fileName, title);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @category 导出计件工资汇总表
	 *2016 2016年6月21日下午1:30:16
	 *void
	 *宋银海
	 */
	public void exportPieceworkSum(){
		try {
			String depUid=getParameter("depUid");
			String personGid=getParameter("personGid");
			String groupGid=getParameter("groupGid");
			String startMonth=getParameter("startMonth");
			String endMonth=getParameter("endMonth");
			
			Map params=new HashMap();
			params.put("depUid", depUid);
			params.put("personGid", personGid);
			params.put("groupGid", groupGid);
			params.put("startMonth", startMonth);
			params.put("endMonth", endMonth);
			params.put("pageIndex", 0);
			params.put("pageSize", 0);
			
			PageBean data = reportFormService.getPieceworkSum(params);
			
			String fileName = "计件工资汇总表-"+DateUtil.dateToString(new Date(),"yyyy-MM-dd");
			String title = "计件工资汇总表";
			String column_str = "{'personCode':'人员编码','personName':'人员名称',"
					+ "'groupCode':'班组编码','groupName':'班组名称','deptName':'部门名称','disNum':'开工数量','reportOkNum':'报工数量','totalPrice':'合计金额'}";
			String column_ext_type = "{'disNum':"+HSSFCell.CELL_TYPE_NUMERIC+",'reportOkNum':"+HSSFCell.CELL_TYPE_NUMERIC+",'totalPrice':"+HSSFCell.CELL_TYPE_NUMERIC+"}";
			exportExcel(column_str, data.getList(), fileName, title,column_ext_type);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 生产订单状况表
	 *2016 2016年6月16日上午9:18:27
	 *String
	 *宋银海
	 */
	public String getOrderStatusList(){
		try{
			
			int pageIndex = getPageIndex();
			int pageSize = getPageSize();
			String keyWord=getParameter("keyWord");
			String startDate=getParameter("startDate");
			String endDate=getParameter("endDate");
			
			Map params=new HashMap();
			params.put("pageIndex", pageIndex);
			params.put("pageSize", pageSize);
			params.put("keyWord", keyWord);
			params.put("startDate", startDate);
			params.put("endDate", endDate);
			
			PageBean pageBean=reportFormService.getOrderStatusList(params);
			
			
			setRequstAttribute("data", pageBean);
			setRequstAttribute("keyWord", keyWord);
			setRequstAttribute("startDate", startDate);
			setRequstAttribute("endDate", endDate);
			return "getOrderStatusList";
		}catch(Exception e){
			e.printStackTrace();
			return "getOrderStatusList";
		}

	}
	
	public String getOrderDetailList(){
		try {
			int pageIndex = getPageIndex();
			int pageSize = getPageSize();
			String keyWord=getParameter("keyWord");
			String startDate=getParameter("startDate");
			String endDate=getParameter("endDate");
			
			Map params=new HashMap();
			params.put("pageIndex", pageIndex);
			params.put("pageSize", pageSize);
			params.put("keyWord", keyWord);
			params.put("startDate", startDate);
			params.put("endDate", endDate);
			
			PageBean pageBean=reportFormService.getOrderDetailList(params);
			
			setRequstAttribute("data", pageBean);
			setRequstAttribute("keyWord", keyWord);
			setRequstAttribute("startDate", startDate);
			setRequstAttribute("endDate", endDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "orderDetailList";
	}
	
}
