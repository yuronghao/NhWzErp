package com.emi.wms.servicedata.action;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DecimalFormat;
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
import com.emi.wms.bean.YmUser;
import com.emi.wms.servicedata.service.SaleService;

/**
 * 销售订单
 * 
 * @author Administrator
 * 
 */
@SuppressWarnings("serial")
public class SaleAction extends BaseAction {
	private SaleService saleService;
	private CacheCtrlService cacheCtrlService;

	public SaleService getSaleService() {
		return saleService;
	}

	public CacheCtrlService getCacheCtrlService() {
		return cacheCtrlService;
	}

	public void setSaleService(SaleService saleService) {
		this.saleService = saleService;
	}

	public void setCacheCtrlService(CacheCtrlService cacheCtrlService) {
		this.cacheCtrlService = cacheCtrlService;
	}

	/**
	 * 销售订单列表
	 * 
	 * @category 年12月10日 下午5:26:16
	 * @author 杨胜
	 * @return
	 */
	public String getSalesList() {
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
			PageBean bean = saleService.getWmSaleorderList(pageIndex, pageSize,
					condition);
			getRequest().setAttribute("data", bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "saleList";
	}

	/**
	 * 获得销售单单号
	 * 
	 * @category 年12月10日 下午5:26:09
	 * @author 杨胜
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void getBillId() {
		try {
		String billType = getRequest().getParameter("billType");// 与WM_BillType表相匹配
		int year = DateUtil.getToYear();
		int month = DateUtil.getToMonth();
		String strMonth;
		if (String.valueOf(month).length() == 1) {
			strMonth = "0" + String.valueOf(month);
		} else {
			strMonth = String.valueOf(month);
		}

		String currentId = saleService.getBillId(billType, year + strMonth);

		String billId = billType + year + strMonth + currentId;

		String nowDate = DateUtil.dateToString(new Date(), "yyyy-MM-dd");
		String userId = getSession().get("UserId").toString();
		YmUser Record = cacheCtrlService.getUser(userId);// 从缓存查出录入人
		
		Map map = new HashMap();
		map.put("nowDate", nowDate);
		map.put("billId", billId);
		map.put("billId", billId);

		map.put("recordname", Record.getUserName());
		map.put("gRecordPersonUid", Record.getGid());

			getResponse().getWriter().write(
					JSONObject.fromObject(map).toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 返回添加或者修改销售订单页面
	 * 
	 * @category 年12月10日 下午5:25:59
	 * @author 杨胜
	 * @return
	 */
	public String getSaleAdd() {

		try {
			String saleOrderGid = getParameter("gid");
			String id = getParameter("id");
			Map saleOrder = null;
			AaDepartment dept = null;
			AaProviderCustomer customer = null;
			AaPerson person = null;
			YmUser Record = null;
			YmUser Audit = null;
			List<Map> saleOrderCList=null;
			List<AaGoods> goods=null;
			String consql = "";
			if (CommonUtil.notNullString(saleOrderGid)) {
				consql += " and s.gid='"+saleOrderGid+"'";
				saleOrder = saleService.findWMSaleOrder(consql);
			} else {
				consql += " order by pk desc";
				saleOrder = saleService.findWMSaleOrder(consql);
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
				saleOrderCList = saleService.findWmSaleorderC(saleOrder
						.get("gid").toString());
			}
			if(!CommonUtil.isNullObject(saleOrderCList))
			{
				String[] goodsList = new String[saleOrderCList.size()];
				for (int i = 0; i < saleOrderCList.size(); i++) {
					goodsList[i] = Constants.CACHE_GOODS + "_"
							+ saleOrderCList.get(i).get("goodsUid").toString();
				}
				 goods = cacheCtrlService.getGoodsList(goodsList);// 从缓存查出商品
			}
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
		return "saleAdd";
	}

	/**
	 * 添加或者修改销售订单
	 * 
	 * @category 年12月10日 下午5:25:45
	 * @author 杨胜
	 */

	/*@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addSale() {
		// 销售订单主表数据begin=====================================
		try {
			String gid = getParameter("gid");
			if (CommonUtil.isNullString(gid)) {
				gid = UUID.randomUUID().toString();
			}
			WmSaleorder order = new WmSaleorder();
			order.setGid(gid);
			order.setNotes(getRequest().getParameter("notes"));// 备注
			order.setCustomeruid(getRequest().getParameter("customerUid"));// 客户uid
			order.setWhuid(getRequest().getParameter("whUid"));// 仓库gid
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
			order.setPersonuid(getRequest().getParameter("personUid"));
			//order.setDepartmentuid(getRequest().getParameter("departmentUid"));
			// 销售订单主表数据end=======================================

			// 销售订单子表数据begin=====================================
			List<WmSaleorderC> order_cList = new ArrayList<WmSaleorderC>();
			List<WmSaleorderC> order_addList = new ArrayList<WmSaleorderC>();
			int cSize = Integer.parseInt(getRequest().getParameter("cSize"));// 得到子表的数据条数
			WmSaleorderC order_c = null;
			for (int i = 1; i <= cSize; i++) {
				order_c = new WmSaleorderC();
				// flag=0原始数据, flag=1修改后的数据,flag=2新添加的数据
				String flag = getRequest().getParameter("flag" + i);
				int flagInt = 0;
				if (flag != null) {
					flagInt = Integer.parseInt(flag);
				}
				if (flagInt != 0) {
					//order_c.setSaleorderuid(gid);
					//order_c.setGoodsuid(getRequest().getParameter(
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
					// 计划到货日
					//order_c.setPlandg(new Timestamp(DateUtil.stringtoDate(
							//getRequest().getParameter("planDG" + i),
							//DateUtil.LONG_DATE_FORMAT).getTime()));
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
				ok = saleService.addWMSaleOrder(order, order_cList);
			} else {
				ok = saleService.updateWMSaleOrder(order, order_cList,
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
*/
	/**
	 * 删除销售订单 物理删除
	 * 
	 * @category 年12月10日 下午5:25:27
	 * @author 杨胜
	 */
	public void deleteSale() {
		try{
			String gid=getRequest().getParameter("gid");
			   
			WmSaleorder order = new WmSaleorder();
			order.setGid(gid);
			//order.setIsdel(1);
			boolean suc =saleService.updateWMSaleOrder(order);
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
	 * @category 年12月10日 下午5:25:02
	 * @author 杨胜
	 * @return
	 */
	public String getMaterielHelp() {
		List<Map> goodsSorts = null;
		getRequest().setAttribute("goodsSorts",
				JSONArray.fromObject(goodsSorts));
		return "materielHelp";
	}
	/*
	 * 自动计算页面价格
	 */
	public void calculate()
	{
		getResponse().setCharacterEncoding("UTF-8");
		String value = getRequest().getParameter("value");
		/*
		 * 0:原币含税单价，1:原币含税金额，2:原币不含税单价，3:原币不含税金额，4:原币税额
		 * 5:本币含税单价，6:本币含税金额，7:本币不含税单价，8:本币不含税金额，9:本币税额
		 */
		int valueIndex = Integer.parseInt(getRequest().getParameter(
				"valueIndex")); // 第几个参数
		String s_quantity = getRequest().getParameter("quantity");// 数量
		String s_exchageRate = getRequest().getParameter("exchangeRate");// 汇率
		String s_rate = getRequest().getParameter("rate");// 税率

		try
		{
			BigDecimal unitPrice = new BigDecimal(0d); // 本币含税单价
			BigDecimal quantity = new BigDecimal(Double.parseDouble(s_quantity));
			BigDecimal exchageRate = new BigDecimal(
					Double.parseDouble(s_exchageRate));
			BigDecimal rate = new BigDecimal(Double.parseDouble(s_rate))
					.divide(new BigDecimal(100));
			if (!CommonUtil.isNullString(value))
			{
				BigDecimal one = new BigDecimal(1);
				BigDecimal val = new BigDecimal(Double.parseDouble(value)); // 原币含税单价
				if (valueIndex == 0)
				{
					unitPrice = val.divide(exchageRate, 6,
							RoundingMode.HALF_DOWN);
				} else if (valueIndex == 1)
				{
					unitPrice = val.divide(quantity, 6, RoundingMode.HALF_DOWN)
							.divide(exchageRate, 6, RoundingMode.HALF_DOWN);
				} else if (valueIndex == 2)
				{
					unitPrice = val.multiply(rate.add(one)).divide(exchageRate,
							6, RoundingMode.HALF_DOWN);
				} else if (valueIndex == 3)
				{
					unitPrice = val.divide(quantity, 6, RoundingMode.HALF_DOWN)
							.multiply(rate.add(one))
							.divide(exchageRate, 6, RoundingMode.HALF_DOWN);
				} else if (valueIndex == 4)
				{// X=val/(rate*quantity)*(1+rate)
					unitPrice = val
							.divide(rate.multiply(quantity), 6,
									RoundingMode.HALF_DOWN)
							.multiply(one.add(rate))
							.divide(exchageRate, 6, RoundingMode.HALF_DOWN);
					// unitPrice =
					// val.divide(quantity,6,RoundingMode.HALF_DOWN).divide(rate).divide(exchageRate,6,RoundingMode.HALF_DOWN);
				} else if (valueIndex == 5)
				{
					unitPrice = val;
				} else if (valueIndex == 6)
				{
					unitPrice = val.divide(quantity, 6, RoundingMode.HALF_DOWN);
				} else if (valueIndex == 7)
				{
					unitPrice = val.multiply(rate.add(one));
				} else if (valueIndex == 8)
				{
					unitPrice = val.multiply(rate.add(one)).divide(quantity, 6,
							RoundingMode.HALF_DOWN);
				} else if (valueIndex == 9)
				{
					unitPrice = val.divide(rate.multiply(quantity), 6,
							RoundingMode.HALF_DOWN).multiply(one.add(rate));
				}
			}
			BigDecimal[] values = priceValue(unitPrice, rate, exchageRate,
					quantity);
			
			DecimalFormat dj = new DecimalFormat("#,##0.0000");
			
			DecimalFormat je = new DecimalFormat("#,##0.00");
		

			getResponse().getWriter().write(
					"{'success':'1'," + "'value0':'" + dj.format(values[0].doubleValue()) 
							+ "','value1':'" + je.format(values[1].doubleValue()) 
							+ "','value2':'" + dj.format(values[2].doubleValue())
							+ "','value3':'" + je.format(values[3].doubleValue()) 
							+ "','value4':'" + je.format(values[4].doubleValue())
							+ "','value5':'" + dj.format(values[5].doubleValue())
							+ "','value6':'" + je.format(values[6].doubleValue())
							+ "','value7':'" + dj.format(values[7].doubleValue())
							+ "','value8':'" + je.format(values[8].doubleValue())
							+ "','value9':'" + je.format(values[9].doubleValue()) + "'}");
		} catch (NumberFormatException e)
		{
			try
			{
				getResponse().getWriter().write("{'success':'0','msg':'无效数字'}");
			} catch (IOException e1)
			{
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/*
	 * 传入第一个价格(本币含税单价)，将其作为基础运算
	 * value0:原币含税单价，1:原币含税金额，2:原币不含税单价，3:原币不含税金额，4:原币税额
	 * 5:本币含税单价，6:本币含税金额，7:本币不含税单价，8:本币不含税金额，9:本币税额
	 */
	public BigDecimal[] priceValue(BigDecimal unitPrice, BigDecimal rate,
			BigDecimal exchageRate, BigDecimal quantity)
	{
		BigDecimal one = new BigDecimal(1);
		BigDecimal[] values = new BigDecimal[10];
		values[0] = unitPrice.multiply(exchageRate);
		values[1] = unitPrice.multiply(quantity).multiply(exchageRate);
		values[2] = unitPrice.divide(one.add(rate), 6, RoundingMode.HALF_DOWN)
				.multiply(exchageRate);
		values[3] = unitPrice.divide(one.add(rate), 6, RoundingMode.HALF_DOWN)
				.multiply(quantity).multiply(exchageRate);
		values[4] = unitPrice.multiply(quantity).multiply(rate)
				.divide(one.add(rate), 6, RoundingMode.HALF_DOWN)
				.multiply(exchageRate);
		values[5] = unitPrice;
		values[6] = unitPrice.multiply(quantity);
		values[7] = unitPrice.divide(one.add(rate), 6, RoundingMode.HALF_DOWN);
		values[8] = unitPrice.divide(one.add(rate), 6, RoundingMode.HALF_DOWN)
				.multiply(quantity);
		values[9] = unitPrice.multiply(quantity).multiply(rate)
				.divide(one.add(rate), 6, RoundingMode.HALF_DOWN);

		return values;
	}

}
