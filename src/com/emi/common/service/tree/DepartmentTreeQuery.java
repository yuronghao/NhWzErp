package com.emi.common.service.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import com.emi.common.bean.core.TreeNode;
import com.emi.common.bean.core.ITreeQuery;
import com.emi.sys.core.bean.PageBean;
/**
 * @Title:物料
 * @Copyright: Copyright (c) v1.0
 * @Company: 江苏一米智能科技股份有限公司
 * @project name: LugErp
 * @author: zhuxiaochen
 * @version: V1.0
 * @time: 2016年4月22日 上午9:51:14
 */
public class DepartmentTreeQuery extends BaseTreeQuery implements ITreeQuery{

	public DepartmentTreeQuery(HttpServletRequest request) {
		super(request);
	}

	@Override
	public List<TreeNode> getQueryTree() {
		List<TreeNode> list = this.emiPluginDao.getDepartmentTree(sobId,orgId);
		return list;
	}

	@Override
	public PageBean getQueryList() {
		String showTree = this.request.getParameter("showTree");
		String keyWord = this.request.getParameter("keyWord");
		String gid = this.request.getParameter("id");
//		PageBean pagebean = this.emiPluginDao.getDepartmentList(gid,sobId,orgId,this.pageIndex,this.pageSize,showTree,keyWord);
		this.request.setAttribute("keyWord", keyWord);
		return new PageBean();
	}

	@Override
	public List<JSONObject> getColumns() {
		List<JSONObject> list = new ArrayList<JSONObject>();
		//宽度百分比，注意已经有一列‘序号’，占了8%
//		JSONObject c1 = JSONObject.fromObject("{'name':'gid','desc':'','width':'8%','check':'1'}"); //checkbox 
//		list.add(c1);
//		JSONObject c2 = JSONObject.fromObject("{'name':'goodscode','desc':'物料编码','width':'20%','check':'0'}");
//		list.add(c2);
//		JSONObject c3 = JSONObject.fromObject("{'name':'goodsname','desc':'物料名称','width':'20%','check':'0'}");
//		list.add(c3);
//		JSONObject c4 = JSONObject.fromObject("{'name':'goodsstandard','desc':'规格','width':'30%','check':'0'}");
//		list.add(c4);
//		JSONObject c5 = JSONObject.fromObject("{'name':'unitName','desc':'单位','width':'','check':'0'}");
//		list.add(c5);
		
		return list;
	}

}
