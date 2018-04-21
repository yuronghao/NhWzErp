package com.emi.wms.servicedata.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.HSSFCell;

import com.emi.android.bean.ProcessReportScanRsp;
import com.emi.android.bean.ProcessStartScanRsp;
import com.emi.android.bean.ProcessTaskDetailSamplingRsp;
import com.emi.common.action.BaseAction;
import com.emi.common.util.CommonUtil;
import com.emi.common.util.DateUtil;
import com.emi.common.util.FileUploadUtils;
import com.emi.sys.core.bean.PageBean;
import com.emi.sys.core.format.EmiJsonObj;
import com.emi.wms.bean.MESWMCostReportInInfor;
import com.emi.wms.bean.MesWmDispatchingorder;
import com.emi.wms.bean.MesWmDispatchingorderc;
import com.emi.wms.bean.MesWmReportorder;
import com.emi.wms.bean.MesWmReportorderc;
import com.emi.wms.bean.QMCheckBill;
import com.emi.wms.bean.QMCheckCReasonBill;
import com.emi.wms.bean.QMCheckCbill;
import com.emi.wms.servicedata.service.CostService;
import com.emi.wms.servicedata.service.ProduceOrderService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;


public class CostAction extends BaseAction implements Serializable
{
	private static final long serialVersionUID = 9060263678701491132L;
	
	private CostService costService;
	public void setCostService(CostService costService) {
		this.costService = costService;
	}
	

	//报工单、入库单取数
	public void getCostReportInInfor(){
		try{
			String res=costService.getCostReportInInfor(getRequest());
			responseWrite(res);
		}catch(Exception e){
			e.printStackTrace();
			writeErrorOrSuccess(0,"失败");
		}
	}
	
	//材料出库单取数
	public void getCostRdRecordsOut(){
		try{
			String res=costService.getCostRdRecordsOut(getRequest());
			responseWrite(res);
		}catch(Exception e){
			e.printStackTrace();
			writeErrorOrSuccess(0,"失败");
		}
	}
	
	//获得工和费
	public void getCostFee(){
		try{
			String res=costService.getCostFee(getRequest());
			responseWrite(res);
			
		}catch(Exception e){
			e.printStackTrace();
			writeErrorOrSuccess(0,"失败");
		}
	}
	
	//获得低阶码运算
	public void getLowOrderCode(){
		try{
			String res=costService.getLowOrderCode(getRequest());
			responseWrite(res);
		}catch(Exception e){
			e.printStackTrace();
			writeErrorOrSuccess(0,"失败");
		}
	}
	
	
	//分摊工和费(从科目表取数 结合成本项目分配率)
	public void shareFee(){
		try{
			String res=costService.shareFee(getRequest());
			responseWrite(res);
		}catch(Exception e){
			e.printStackTrace();
			writeErrorOrSuccess(0,"计算出现错误");
			
		}
	}
	
	
	//根据低阶码进行计算成本计算
	public void calculateCost(){
		try{
			String res=costService.calculateCost(getRequest());
			responseWrite(res);
		}catch(Exception e){
			e.printStackTrace();
			writeErrorOrSuccess(0,"失败");
		}
	}
	
	//报工/入库列表
	public String getReportProductInList(){
		int pageIndex = getPageIndex();
		int pageSize = getPageSize();
		Map mapRes=costService.getReportProductInList(getRequest(), pageIndex, pageSize);
		setRequstAttribute("data", (PageBean)mapRes.get("pageBean"));
		setRequstAttribute("mainGid", mapRes.get("mainGid").toString());
		return "reportProductInList";
	}
	
	
	//费用详细列表
	public String getCostFeeList(){
		Map mapRes=costService.getCostFeeList(getRequest());
		setRequstAttribute("data", mapRes.get("data"));
		setRequstAttribute("mainGid", mapRes.get("mainGid").toString());
		return "costFeeList";
	}
	
	//修改费用项
	public void uptCostFee(){
		try{
			String res=costService.uptCostFee(getRequest());
			responseWrite(res);
		}catch(Exception e){
			e.printStackTrace();
			writeErrorOrSuccess(0,"失败");
		}
	}
	
	
	
	//材料费用详细页
	public String getRdRecordsOutList(){
		int pageIndex = getPageIndex();
		int pageSize = getPageSize();
		Map mapRes=costService.getRdRecordsOutList(getRequest(), pageIndex, pageSize);
		setRequstAttribute("data", mapRes.get("pageBean"));
		setRequstAttribute("mainGid", mapRes.get("mainGid").toString());
		return "rdRecordsOutList";
	}
	
	
	//导出报工单
	public void exportReportProductInList(){
		try {
			String mainGid = getParameter("mainGid");//查询条件-订单号
			
			List riis = costService.getReportProductInListNoPage(mainGid);
			
			String fileName = "报工入库表-"+DateUtil.dateToString(new Date(),"yyyy-MM-dd");
			String title = "报工入库表";
			String column_str = "{'produceOrderCode':'订单编号','billdate':'单据日期','goodsCode':'存货编码','goodsName':'存货名称','cfree1':'自由项','goodsStandard':'规格型号','opname':'报工工序','reportOkNum':'报工合格','reportNotOkNum':'报工不合格',"
					+ "'reportProblemNum':'问题数量','produceInNum':'生产入库','mainLowOrderCode':'一级低阶码','secondLowOrderCode':'二级低阶码',"
					+ "'pricePref001':'上道直接材料1','pricePref002':'上道直接材料2','pricePref003':'上道间接材料','pricePref004':'上道委托加工费用','pricePref005':'上道直接人工','pricePref006':'上道间接人工','pricePref007':'上道燃料动力','pricePref008':'上道折旧摊销','pricePref009':'上道其他费用','pricePref010':'上道二次分配',"
					+ "'f001':'直接材料1','f002':'直接材料2','f003':'间接材料','f004':'委托加工费用','f005':'直接人工','f006':'间接人工','f007':'燃料动力','f008':'折旧摊销','f009':'其他费用','f010':'二次分配',"
					+ "'iunitCostf001':'单价直接材料1','iunitCostf002':'单价直接材料2','iunitCostf003':'单价间接材料','iunitCostf004':'单价委托加工费用','iunitCostf005':'单价直接人工','iunitCostf006':'单价间接人工','iunitCostf007':'单价燃料动力','iunitCostf008':'单价折旧摊销','iunitCostf009':'单价其他费用','iunitCostf010':'单价二次分配'}";
			String column_ext_type = "{'reportOkNum':"+HSSFCell.CELL_TYPE_NUMERIC+",'reportNotOkNum':"+HSSFCell.CELL_TYPE_NUMERIC+",'reportProblemNum':"+HSSFCell.CELL_TYPE_NUMERIC+",'produceInNum':"+HSSFCell.CELL_TYPE_NUMERIC+"}";
			exportExcel(column_str, riis, fileName, title,column_ext_type);
			
		} catch (Exception e) {
			e.printStackTrace();
			writeErrorOrSuccess(0,"失败");
		}
	}
	
	//费用主表
	public String getCostFeeMainList(){
		int pageIndex = getPageIndex();
		int pageSize = getPageSize();
		Map mapRes=costService.getCostFeeMainList(getRequest(), pageIndex, pageSize);
		setRequstAttribute("data", (PageBean)mapRes.get("pageBean"));
		return "costFeeMainList";
	}
	
	//领用列表
	public String getCostRdRecordsOutMain(){
		int pageIndex = getPageIndex();
		int pageSize = getPageSize();
		Map mapRes=costService.getCostRdRecordsOutMain(getRequest(), pageIndex, pageSize);
		setRequstAttribute("data", (PageBean)mapRes.get("pageBean"));
		return "costRdRecordsOutMain";
	}
	
	//报工、入库列表
	public String getCostReportInInforMain(){
		int pageIndex = getPageIndex();
		int pageSize = getPageSize();
		Map mapRes=costService.getCostReportInInforMain(getRequest(), pageIndex, pageSize);
		setRequstAttribute("data", (PageBean)mapRes.get("pageBean"));
		return "costReportInInforMain";
	}
	
	
	//月末结余列表
	public String getCostGoodsBalanceMain(){
		int pageIndex = getPageIndex();
		int pageSize = getPageSize();
		Map mapRes=costService.getCostGoodsBalanceMain(getRequest(), pageIndex, pageSize);
		setRequstAttribute("data", (PageBean)mapRes.get("pageBean"));
		return "costGoodsBalanceMain";
	}
	
	
	//月末结余详情
	public String getCostGoodsBalanceList(){
		int pageIndex = getPageIndex();
		int pageSize = getPageSize();
		Map mapRes=costService.getCostGoodsBalanceList(getRequest(), pageIndex, pageSize);
		setRequstAttribute("data", (PageBean)mapRes.get("pageBean"));
		setRequstAttribute("mainGid", mapRes.get("mainGid").toString());
		return "costGoodsBalanceList";
	}
	
	//获得月末结存
	public void getCostGoodsBalance(){
		try{
			String res=costService.getCostGoodsBalance(getRequest());
			responseWrite(res);
			
		}catch(Exception e){
			e.printStackTrace();
			writeErrorOrSuccess(0,"失败");
		}
	}
	
	
	//工序在制
	public String getCostProcessInWorkMain(){
		int pageIndex = getPageIndex();
		int pageSize = getPageSize();
		Map mapRes=costService.getCostProcessInWorkMain(getRequest(), pageIndex, pageSize);
		setRequstAttribute("data", (PageBean)mapRes.get("pageBean"));
		return "costProcessInWorkMain";
	}
	
	//材料在制
	public String getCostMaterialWorkMain(){
		int pageIndex = getPageIndex();
		int pageSize = getPageSize();
		Map mapRes=costService.getCostMaterialWorkMain(getRequest(), pageIndex, pageSize);
		setRequstAttribute("data", (PageBean)mapRes.get("pageBean"));
		return "costMaterialWorkMain";
	}
	
	
	//直接材料2在制
	public String getCostDirectMaterialWorkMain2(){
		int pageIndex = getPageIndex();
		int pageSize = getPageSize();
		Map mapRes=costService.getCostDirectMaterialWorkMain2(getRequest(), pageIndex, pageSize);
		setRequstAttribute("data", (PageBean)mapRes.get("pageBean"));
		return "costDirectMaterialWorkMain2";
	}
	
	// 工序在制明细列表
	public String getCostProcessInWorkList(){
		int pageIndex = getPageIndex();
		int pageSize = getPageSize();
		Map mapRes=costService.getCostProcessInWorkList(getRequest(), pageIndex, pageSize);
		setRequstAttribute("data", (PageBean)mapRes.get("pageBean"));
		setRequstAttribute("mainGid", mapRes.get("mainGid").toString());
		return "costProcessInWorkList";
	}
	
	//材料在制明细列表
	public String getCostMaterialWorkList(){
		int pageIndex = getPageIndex();
		int pageSize = getPageSize();
		Map mapRes=costService.getCostMaterialWorkList(getRequest(), pageIndex, pageSize);
		setRequstAttribute("data", (PageBean)mapRes.get("pageBean"));
		setRequstAttribute("mainGid", mapRes.get("mainGid").toString());
		return "costMaterialWorkList";
	}
	
	
	//材料2在制明细列表
	public String getCostDirectMaterialWorkList2(){
		int pageIndex = getPageIndex();
		int pageSize = getPageSize();
		Map mapRes=costService.getCostDirectMaterialWorkList2(getRequest(), pageIndex, pageSize);
		setRequstAttribute("data", (PageBean)mapRes.get("pageBean"));
		setRequstAttribute("mainGid", mapRes.get("mainGid").toString());
		return "costDirectMaterialWorkList2";
	}
	
	
	
	//更新u8单据
	public void uptRecords(){
		try{
			String res=costService.uptRecords(getRequest());
			responseWrite(res);
		}catch(Exception e){
			e.printStackTrace();
			writeErrorOrSuccess(0,"失败");
		}
	}
	
	
}
