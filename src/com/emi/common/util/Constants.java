package com.emi.common.util;

public class Constants  {
	public static final int SUCCESS_VALUE = 1; // 成功
	public static final int FAIL_VALUE = 0; // 失败

	public static final int RIGHT_WEB = 0; // 权限类型-网页版权限
	public static final int RIGHT_MOB = 1; // 权限类型-手机版权限

	public static final String SET_SYSTEMS = "rightSystems"; // 权限管理的系统

	public static final String SYSNAME_WMS = "wms"; // 系统名称简称 仓储管理系统
	public static final String SYSNAME_MES = "mes"; // 系统名称简称 生产管理系统

	// ////////////////////////////////////////////////////////////////////////////
	public static final String CACHE_GOODS = "goods";// 物料在缓存中的头标识
	public static final String CACHE_STOCK = "stock";// 物料现存量在缓存中的头标识
	public static final String CACHE_DEPARTMENT = "department";// 部门在缓存中的头标识
	public static final String CACHE_PERSON = "person";// 人员在缓存中的头标识
	public static final String CACHE_WAREHOUSE = "warehouse";// 仓库在缓存中的头标识
	public static final String CACHE_GOODSALLOCATION = "goodsallocation";// 货位在缓存中的头标识
	public static final String CACHE_PROVIDERCUSTOMER = "providercustomer";// 客商在缓存中的头标识
	public static final String CACHE_MESWORKCENTER = "mesworkcenter";// 工作中心在缓存中的头标识
	public static final String CACHE_MESWORKSTATION = "mesworkstation";// 工作中心在缓存中的头标识
	public static final String CACHE_MESEQUIPMENT = "mesequipment";// 设备在缓存中的头标识
	public static final String CACHE_MOULD = "mould";// 设备在缓存中的头标识
	public static final String CACHE_GROUP = "group";// 设备在缓存中的头标识
	public static final String CACHE_MESWORKEQUIPMENTLIST = "mesworkequipmentlist";// 工作中心所属的设备
	public static final String CACHE_MESSTANDARDPROCESS="messtandardprocess";//工序在缓存中的头标识
	public static final String CACHE_ALLOCATIONSTOCK="allocationstock";//货位现存量在缓存中的头标识
	public static final String CACHE_ALLOCATIONSTOCKLIST="allocationstocklist";//货位现存量List在缓存中的头标识
	public static final String CACHE_USER="user";//用户在缓存中的头标识
	public static final String CACHE_SESSION="session";//session在缓存中的头标识
	public static final String CACHE_ORG="org";//组织在缓存中的头标识
	public static final String CACHE_CLASSIFY="classify";//类别在缓存中的头标识
	public static final String CACHE_UNIT="unit";//单位在缓存中的头标识
	
	
	public static void main(String[] args) {
		String str = "AllocationStockList";
		System.out.println(str.toUpperCase());
		System.out.println(str.toLowerCase());
	}

	/*
	 * ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 功能模块的代码
	 * ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	 */
	public static final String RI_CODE_USER = "wms_010101"; // 用户信息
	public static final String RI_CODE_ORG = "wms_010201"; // 组织信息
	public static final String RI_CODE_DEPARTMENT = "wms_010202"; // 部门信息
	public static final String RI_CODE_PERSON = "wms_010203"; // 人员信息
	public static final String RI_CODE_RIGHT = "wms_010301"; // 模块管理
	public static final String RI_CODE_ROLE = "wms_010302"; // 角色管理
	public static final String RI_CODE_FLOWBEGIN = "flow_0101"; // 流程发起
	public static final String RI_CODE_TODO = "flow_0102"; // 待办工作
	public static final String RI_CODE_DONE = "flow_0103"; // 已办工作
	public static final String RI_CODE_FLOWDESIGN = "flow_0201"; // 流程设计
	public static final String RI_CODE_PROJECT = "ms_0101"; // 工程管理
	public static final String RI_CODE_SUBJECT = "ms_0102"; // 项目管理
	public static final String RI_CODE_PROJECTFILE = "ms_0103"; // 文件上传
	public static final String RI_CODE_PAYREPORT = "ms_0104"; // 计量支付上报
	public static final String RI_CODE_PAY = "ms_0105"; // 付款
	public static final String RI_CODE_PROVIDERSORT = "wms_020301"; // 供应商分类
	public static final String RI_CODE_PROVIDER = "wms_020302"; // 供应商
	/*
	 * ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ 功能模块的代码
	 * ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	 */

	
	////////////////////////////////////////////////////////////任务类型开始
	public static String TASKTYPE_CGZJ = "0008";//采购质检等
	public static String TASKTYPE_CGTHCK = "0009";//采购退货出库
	public static String TASKTYPE_CGRK = "0010";//任务类型：采购入库
	public static String TASKTYPE_CPZJ = "0019";//成品质检
	public static String TASKTYPE_CPRK = "0020";//任务类型：成品入库
	public static String TASKTYPE_QTRK = "0030";//任务类型：其他入库
	public static String TASKTYPE_XSZJ = "0038";//任务类型：销售出库质检
	public static String TASKTYPE_XSTHRK = "0039";//销售退货入库
	public static String TASKTYPE_XSCK = "0040";//任务类型：销售出库
	public static String TASKTYPE_CLCK = "0050";//任务类型：材料出库
	public static String TASKTYPE_QTCK = "0060";//任务类型：其他出库
	public static String TASKTYPE_CGDH = "0070";//任务类型：采购到货
	public static String TASKTYPE_PD = "0080";//任务类型：盘点
	public static String TASKTYPE_DBRK = "0090";//任务类型：调拨入库
	public static String TASKTYPE_DBD = "0100";//任务类型：调拨单 挑拨出库
	public static String TASKTYPE_XSFH = "0110";//任务类型：销售发货
	public static String TASKTYPE_CGDD = "0120";//任务类型:采购订单
	public static String TASKTYPE_XSDD = "0130";//任务类型:销售订单
	public static String TASKTYPE_SCDD = "0140";//生产订单
	public static String TASKTYPE_WWCLCK = "0150";//委外材料出库
	public static String TASKTYPE_WWCPZJ = "0160";//委外成品质检
	public static String TASKTYPE_WWCPRK = "0170";//委外成品入库
	public static String TASKTYPE_WWCPRKTK = "0175";//委外成品退库
	public static String TASKTYPE_WWDD = "0180";//委外订单
	public static String TASKTYPE_PGD = "0185";//工序开工
	public static String TASKTYPE_BGZJ = "0190";//报工质检任务
	public static String TASKTYPE_BGD = "0200";//报工单任务
	public static String TASKTYPE_GXLL = "0210";//工序领料
	public static String TASKTYPE_CZLL = "0215";//称重领料
	public static String TASKTYPE_GXTL = "0220";//工序退料
	public static String TASKTYPE_SJ = "0230";//首检
	public static String TASKTYPE_XJ = "0240";//巡检
	public static String TASKTYPE_CJ = "0250";//抽检
	public static String TASKTYPE_DDGYZB = "0145";//订单工艺路线子表
	public static String TASKTYPE_MOULDINFOR = "0260";//模具查询
	
	
   ////////////////////////////////////////////////////////////任务类型结束
	
	
   ////////////////////////////////////////////////////////////单据来源开始
   	public static String BILLGIDSOURCE_CGD="5"; //单据来源：采购订单
	public static String BILLGIDSOURCE_CGDH="10"; //单据来源：采购到货单
	public static String BILLGIDSOURCE_CGZJ="20"; //单据来源：采购质检单
	public static String BILLGIDSOURCE_XSZJ="28"; //单据来源：销售发货质检单
	public static String BILLGIDSOURCE_XSFH="30"; //单据来源：销售发货单
	public static String BILLGIDSOURCE_SCDD="40"; //单据来源：生产订单
	public static String BILLGIDSOURCE_SCDDGY="50"; //单据来源：生产订单工艺表子表
	public static String BILLGIDSOURCE_PGD="55"; //单据来源：派工单
	public static String BILLGIDSOURCE_BGZJ="60"; //单据来源：报工质检单
	public static String BILLGIDSOURCE_BGD="65"; //单据来源：报工单
	public static String BILLGIDSOURCE_CPZJ="70"; //单据来源：产品质检单
	public static String BILLGIDSOURCE_WWDD="75"; //单据来源：委外订单
	public static String BILLGIDSOURCE_WWZJ="80"; //单据来源：委外质检
	public static String BILLGIDSOURCE_WWDH="85"; //单据来源：委外到货
  ////////////////////////////////////////////////////////////单据来源结束
	

  ///////////////////////////////////////////////////////////开工报工任务条码前缀开始
	public static String BARCODE_PERSON="P";//人员条码前缀
	public static String BARCODE_WORKCENTER="W";//工作组 工作中心前缀
	public static String BARCODE_STATION="S";//工位条码前缀
	public static String BARCODE_PROCESSTASK="T";//任务条码前缀
	public static String BARCODE_MOULD="M";//模具条码前缀
  ///////////////////////////////////////////////////////////开工报工任务条码前缀结束
	
	public static String INTERFACE_U890 = "u890";
	public static String INTERFACE_U8101 = "u8101";
	public static String INTERFACE_U8120 = "u8120";
	public static String INTERFACE_TPLUSE = "t+";
	public static String INTERFACE_O2O = "O2O";
}
