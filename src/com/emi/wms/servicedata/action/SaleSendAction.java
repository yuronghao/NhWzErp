package com.emi.wms.servicedata.action;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.emi.cache.service.CacheCtrlService;
import com.emi.common.action.BaseAction;
import com.emi.common.util.CommonUtil;
import com.emi.common.util.Constants;
import com.emi.common.util.DateUtil;
import com.emi.sys.core.bean.PageBean;
import com.emi.wms.bean.AaDepartment;
import com.emi.wms.bean.AaGoods;
import com.emi.wms.bean.AaPerson;
import com.emi.wms.bean.AaProviderCustomer;
import com.emi.wms.bean.WmSaleorder;
import com.emi.wms.bean.WmSaleorderC;
import com.emi.wms.bean.WmSalesend;
import com.emi.wms.bean.WmSalesendC;
import com.emi.wms.bean.YmUser;
import com.emi.wms.servicedata.service.SaleSendService;
import com.emi.wms.servicedata.service.SaleService;

/**
 * 销售发货单
 * 
 * @author Administrator
 * 
 */
@SuppressWarnings("serial")
public class SaleSendAction extends BaseAction {
	private SaleSendService saleSendService;
	private CacheCtrlService cacheCtrlService;

	public void setSaleSendService(SaleSendService saleSendService) {
		this.saleSendService = saleSendService;
	}

	/**
	 * 销售发货单列表
	 * 
	 * @category 年12月14日 下午5:26:16
	 * @author 杨胜
	 * @return
	 */
	public String getSalesSendList() {
		try {
			int pageIndex = getPageIndex(); // 页码，从1开始
			int pageSize = getPageSize();// getPageSize(); //每页总条数
			String orgId = getSession().get("OrgId").toString();
			String sobId = getSession().get("SobId").toString();
			String condition = " and s.sobGid='" + sobId + "' and s.orgGid='"
					+ orgId + "'";
			if (CommonUtil.notNullString(getParameter("sname"))) {
				condition += " and s.souname like '%" + getParameter("sname")
						+ "%'";
			}
			PageBean bean = saleSendService.getWmSalesendList(pageIndex, pageSize,
					condition);
			getRequest().setAttribute("data", bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "saleSendList";
	}

	/**
	 * 获得销售发货单单号
	 * 
	 * @category 年12月14日 下午5:26:09
	 * @author 杨胜
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void getBillId() {

		String billType = "13";// 与WM_BillType表相匹配
		int year = DateUtil.getToYear();
		int month = DateUtil.getToMonth();
		String strMonth;
		if (String.valueOf(month).length() == 1) {
			strMonth = "0" + String.valueOf(month);
		} else {
			strMonth = String.valueOf(month);
		}

		String currentId = saleSendService.getBillId(billType, year + strMonth);

		String billId = billType + year + strMonth + currentId;

		String nowDate = DateUtil.dateToString(new Date(), "yyyy-MM-dd");
		String userId = getSession().get("UserId").toString();
		YmUser Record = cacheCtrlService.getUser(userId);// 从缓存查出录入人
		Map map = new HashMap();
		map.put("nowDate", nowDate);
		map.put("billId", billId);
		map.put("recordname", Record.getUserName());
		map.put("gRecordPersonUid", Record.getGid());
		try {
			getResponse().getWriter().write(
					JSONObject.fromObject(map).toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 返回添加或者修改销售发货单页面
	 * 
	 * @category 年12月14日 下午5:25:59
	 * @author 杨胜
	 * @return
	 */
	public String getSaleSendAdd() {

		try {
			String saleOrderGid = getParameter("gid");
			String id = getParameter("id");
			Map saleOrder = null;
			AaDepartment dept = null;
			AaProviderCustomer customer = null;
			AaPerson person = null;
			YmUser Record = null;
			YmUser Audit = null;
			String consql = "";
			if (CommonUtil.notNullString(saleOrderGid)) {
				consql += "";
				saleOrder = saleSendService.findWmSalesend(consql);
			} else {
				consql += "";
				saleOrder = saleSendService.findWmSalesend(consql);
			}
			if (!CommonUtil.isNullObject(saleOrder)) {
				dept = cacheCtrlService.getDepartment(saleOrder.get(
						"departmentUid").toString());// 从缓存查出部门
				customer = cacheCtrlService.getProviderCustomer(saleOrder.get(
						"customerUid").toString());// 从缓存查出客户
				person = cacheCtrlService.getPerson(saleOrder.get("personUid")
						.toString());// 从缓存查出业务员
				Record = cacheCtrlService.getUser(saleOrder.get(
						"recordPersonUid").toString());// 从缓存查出录入人
				if (!CommonUtil.isNullObject(saleOrder.get("auditPersonUid")
						.toString())) {
					Audit = cacheCtrlService.getUser(saleOrder.get(
							"auditPersonUid").toString());// 从缓存查出审核人
				}
			}
			List<Map> saleOrderCList = saleSendService.findWmSalesendC(saleOrder
					.get("gid").toString());
			String[] goodsList = new String[saleOrderCList.size()];
			for (int i = 0; i < saleOrderCList.size(); i++) {
				goodsList[i] = Constants.CACHE_GOODS + "_"
						+ saleOrderCList.get(i).get("goodsUid").toString();
			}
			List<AaGoods> goods = cacheCtrlService.getGoodsList(goodsList);// 从缓存查出商品

			getRequest().setAttribute("saleOrderMap", saleOrder);
			getRequest().setAttribute("saleOrderCList", saleOrderCList);
			getRequest().setAttribute("Audit", Audit);
			getRequest().setAttribute("Record", Record);
			getRequest().setAttribute("dept", dept);
			getRequest().setAttribute("customer", customer);
			getRequest().setAttribute("person", person);
			getRequest().setAttribute("goods", goods);
		} catch (Exception e) {
			e.printStackTrace();

		}
		return "saleSendAdd";
	}

	/**
	 * 添加或者修改销售发货单
	 * 
	 * @category 年12月14日 下午5:25:45
	 * @author 杨胜
	 */

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addSaleSend() {
		// 销售订单主表数据begin=====================================
		try {
			String gid = getParameter("gid");
			if (CommonUtil.isNullString(gid)) {
				gid = UUID.randomUUID().toString();
			}
			WmSalesend order = new WmSalesend();
			order.setGid(gid);
			order.setNotes(getRequest().getParameter("notes"));// 备注
			order.setCustomeruid(getRequest().getParameter("customerUid"));// 客户uid
			//order.setWhuid(getRequest().getParameter("whUid"));// 仓库gid
			String cSaleType;
			if (getRequest().getParameter("saleType").equals("0")) {
				cSaleType = "销售类型";
			} else {
				cSaleType = "成品销售";
			}
			order.setSaletype(cSaleType);// 销售类型
			String cCurrency;
			if (getRequest().getParameter("currency").equals("0")) {
				cCurrency = "人民币";
			} else {
				cCurrency = "美元";
			}
			order.setCurrency(cCurrency);// 币种
			order.setBillstate("0"); // 单据状态
			order.setExchangerate(new BigDecimal(getRequest().getParameter(
					"exchangeRate")));// 汇率
			order.setRate(new BigDecimal(getRequest().getParameter("rate")));// 税率
			order.setTransportation(getRequest().getParameter("transportation"));// 运输方式
			String billCode = getRequest().getParameter("billCode");
			order.setBillcode(billCode);// 单据号
			if (!CommonUtil.isNullString(getRequest().getParameter("billDate")))// 单据日期
				order.setBilldate(new Timestamp(DateUtil.stringtoDate(
						getRequest().getParameter("billDate"),
						DateUtil.LONG_DATE_FORMAT).getTime()));// 单据时间
			order.setRecordpersonuid(getSession().get("UserId").toString());// 录入人
			order.setRecorddate(new Timestamp(DateUtil.stringtoDate(
					getRequest().getParameter("recordDate"),
					DateUtil.LONG_DATE_FORMAT).getTime()));// 录入日期
			order.setRecorddate(new Timestamp(new Date().getTime()));// 录入时间
			order.setBarcode(billCode);// 条码
			// 销售订单主表数据end=======================================

			// 销售订单子表数据begin=====================================
			List<WmSalesendC> order_cList = new ArrayList<WmSalesendC>();
			List<WmSalesendC> order_addList = new ArrayList<WmSalesendC>();
			int cSize = Integer.parseInt(getRequest().getParameter("cSize"));// 得到子表的数据条数
			WmSalesendC order_c = null;
			for (int i = 1; i <= cSize; i++) {
				order_c = new WmSalesendC();
				// flag=0原始数据, flag=1修改后的数据,flag=2新添加的数据
				String flag = getRequest().getParameter("flag" + i);
				int flagInt = 0;
				if (flag != null) {
					flagInt = Integer.parseInt(flag);
				}
				if (flagInt != 0) {
					order_c.setSalesenduid(gid);
					order_c.setGoodsuid(getRequest().getParameter(
							"goodsUid" + i));// 物品uid
					// 数量
					order_c.setNumber(new BigDecimal(getRequest().getParameter(
							"number" + i)));
					// 报价（本币）
					order_c.setLocalprice(new BigDecimal(getRequest()
							.getParameter("localPrice" + i)));
					// 原币含税单价
					order_c.setOriginaltaxprice(new BigDecimal(getRequest()
							.getParameter("originalTaxPrice" + i)));
					// 原币含税金额
					order_c.setOriginaltaxmoney(new BigDecimal(getRequest()
							.getParameter("originalTaxMoney" + i)));
					// 原币不含税单价
					order_c.setOriginalnotaxprice(new BigDecimal(getRequest()
							.getParameter("originalNotaxPrice" + i)));
					// 原币不含税金额
					order_c.setOriginalnotaxmoney(new BigDecimal(getRequest()
							.getParameter("originalNotaxMoney" + i)));
					// 原币税额
					order_c.setOriginaltax(new BigDecimal(getRequest()
							.getParameter("originalTax" + i)));
					// 本币含税单价
					order_c.setLocaltaxprice(new BigDecimal(getRequest()
							.getParameter("localTaxPrice" + i)));
					// 本币含税金额
					order_c.setLocaltaxmoney(new BigDecimal(getRequest()
							.getParameter("localTaxMoney" + i)));
					// 本币不含税单价
					order_c.setLocalnotaxprice(new BigDecimal(getRequest()
							.getParameter("localNotaxPrice" + i)));
					// 本币不含税金额
					order_c.setLocalnotaxmoney(new BigDecimal(getRequest()
							.getParameter("localNotaxMoney" + i)));
					// 本币税额
					order_c.setLocaltax(new BigDecimal(getRequest()
							.getParameter("localTax" + i)));
					// 原币折扣额
					order_c.setOriginaldeduction(new BigDecimal(getRequest()
							.getParameter("originalDeduction" + i)));
					// 本币折扣额
					order_c.setLocaldeduction(new BigDecimal(getRequest()
							.getParameter("localDeduction" + i)));
					// 扣率
					order_c.setDiscount(new BigDecimal(getRequest()
							.getParameter("discount" + i)));

				}
				// 修改的数据
				if (flagInt == 1) {
					order_c.setGid(getRequest().getParameter("gid" + i));
					order_cList.add(order_c);
				}
				// 新加的数据
				else if (flagInt == 2) {
					String cgid = UUID.randomUUID().toString();
					order_c.setGid(cgid);
					order_addList.add(order_c);
				}
			}
			// 销售订单子表数据end=======================================
			// 获取删除掉的uid,界面传过来时要用''分割好
			String deleteUid = getRequest().getParameter("deleteGids");
			boolean ok = false;
			if (CommonUtil.isNullString(gid)) {
				ok = saleSendService.addWmSalesend(order, order_cList);
			} else {
				ok = saleSendService.updateWmSalesend(order, order_cList,
						order_addList, deleteUid);
			}
			Map map = new HashMap();
			if (ok == true) {
				map.put("success", true);
				map.put("msg", "成功");
			} else {
				map.put("success", false);
				map.put("msg", "成功");
			}
			map.put("gid", gid);

			try {
				getResponse().getWriter().write(
						JSONObject.fromObject(map).toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	/**
	 * 删除销售发货单 物理删除
	 * 
	 * @category 年12月14日 下午5:25:27
	 * @author 杨胜
	 */
	public void deleteSaleSend() {
		try{
			String gid=getRequest().getParameter("gid");
			   
			WmSalesend order = new WmSalesend();
			order.setGid(gid);
			order.setIsdel(1);
			boolean suc =saleSendService.updateWmSalesend(order);
			if (suc) {
				getResponse().getWriter().write("success");
			} else {
				getResponse().getWriter().write("error");
			}
	} catch (Exception e) {
		e.printStackTrace();
	}
	}

	/**
	 * 物料帮助查询
	 * 
	 * @category 年12月14日 下午5:25:02
	 * @author 杨胜
	 * @return
	 */
	public String getMaterielHelp() {
		List<Map> goodsSorts = null;
		getRequest().setAttribute("goodsSorts",
				JSONArray.fromObject(goodsSorts));
		return "materielHelp";
	}

}
