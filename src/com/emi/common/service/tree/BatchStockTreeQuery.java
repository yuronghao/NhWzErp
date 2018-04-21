package com.emi.common.service.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import com.emi.common.bean.core.TreeNode;
import com.emi.common.bean.core.ITreeQuery;
import com.emi.common.util.CommonUtil;
import com.emi.sys.core.bean.PageBean;
import com.emi.wms.bean.AaGoods;

/**
 * @Title:生产订单下的产品列表
 * @Copyright: Copyright (c) v1.0
 * @Company: 江苏一米智能科技股份有限公司
 * @project name: LugErp
 * @author: zhuxiaochen
 * @version: V1.0
 * @time: 2016年4月22日 上午9:51:14
 */
public class BatchStockTreeQuery extends BaseTreeQuery implements ITreeQuery{

	public BatchStockTreeQuery(HttpServletRequest request) {
		super(request);
	}

	@Override
	public List<TreeNode> getQueryTree() {
		List<TreeNode> list = new ArrayList<TreeNode>();
		return list;
	}

	@Override
	public PageBean getQueryList() {
		String showTree = this.request.getParameter("showTree");
		String keyWord = this.request.getParameter("keyWord");
		
		PageBean pagebean=new PageBean();
		String condition="1=1";
		if(CommonUtil.isNullObject(keyWord)){
			return pagebean;
		}else{
			 condition=" po.billCode like '%"+keyWord+"%' and po.sobgid='"+sobId+"' and po.orgGid='"+orgId+"'";
		}
		
		pagebean = this.emiPluginDao.getAllocationStock(condition,this.pageIndex,this.pageSize);
		List<Map> results=pagebean.getList();
		for(Map map:results){
			AaGoods aaGoods=this.getCacheCtrlService().getGoods(map.get("goodsUid").toString());
			map.put("goodsCode", aaGoods.getGoodscode());
			map.put("goodsName", aaGoods.getGoodsname());
			map.put("goodsStandand", aaGoods.getGoodsstandard());
		}
		
		this.request.setAttribute("keyWord", keyWord);

		return pagebean;
	}

	@Override
	public List<JSONObject> getColumns() {
		List<JSONObject> list = new ArrayList<JSONObject>();
		//宽度百分比，注意已经有一列‘序号’，占了8%
		JSONObject c1 = JSONObject.fromObject("{'name':'pocgid','desc':'','width':'9%','check':'1'}"); //checkbox 
		list.add(c1);
		JSONObject c2 = JSONObject.fromObject("{'name':'billDate','desc':'单据日期','width':'13%','check':'0'}");
		list.add(c2);
		JSONObject c3 = JSONObject.fromObject("{'name':'goodsCode','desc':'存货编码','width':'13%','check':'0'}");
		list.add(c3);
		JSONObject c4 = JSONObject.fromObject("{'name':'goodsName','desc':'存货名称','width':'13%','check':'0'}");
		list.add(c4);
		JSONObject c5 = JSONObject.fromObject("{'name':'goodsStandand','desc':'规格','width':'13%','check':'0'}");
		list.add(c5);
		JSONObject c6 = JSONObject.fromObject("{'name':'number','desc':'下达生产量','width':'13%','check':'0','hidde':'1'}");
		list.add(c6);
		JSONObject c7 = JSONObject.fromObject("{'name':'startDate','desc':'计划开工日','width':'13%','check':'0','hidde':'1'}");
		list.add(c7);
		JSONObject c8 = JSONObject.fromObject("{'name':'endDate','desc':'计划完工日','width':'13%','check':'0','hidde':'1'}");
		list.add(c8);
		JSONObject c9 = JSONObject.fromObject("{'name':'billCode','desc':'单据编号','width':'13%','check':'0','hidde':'1'}");
		list.add(c9);
		
		return list;
	}

}
