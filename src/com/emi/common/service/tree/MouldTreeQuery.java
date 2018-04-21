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
 * @Title:工作中心
 * @Copyright: Copyright (c) v1.0
 * @Company: 江苏一米智能科技股份有限公司
 * @project name: LugErp
 * @author: zhuxiaochen
 * @version: V1.0
 * @time: 2016年4月22日 上午9:51:14
 */
public class MouldTreeQuery extends BaseTreeQuery implements ITreeQuery{

	public MouldTreeQuery(HttpServletRequest request) {
		super(request);
	}

	@Override
	public List<TreeNode> getQueryTree() {
		List<TreeNode> list = this.emiPluginDao.getMouldTree(sobId,orgId);
		return list;
	}

	@Override
	public PageBean getQueryList() {
		String showTree = this.request.getParameter("showTree");
		String keyWord = this.request.getParameter("keyWord");
		String gid = this.request.getParameter("id");
//		PageBean pagebean = this.emiPluginDao.getMouldList( keyWord,sobId,orgId,this.pageIndex,this.pageSize,showTree);
		PageBean pagebean = this.emiPluginDao.getMouldList(gid,sobId,orgId,this.pageIndex,this.pageSize,showTree,keyWord);
		this.request.setAttribute("keyWord", keyWord);
		
		return pagebean;
	}

	@Override
	public List<JSONObject> getColumns() {
		List<JSONObject> list = new ArrayList<JSONObject>();
		//宽度百分比，注意已经有一列‘序号’，占了8%
		JSONObject c1 = JSONObject.fromObject("{'name':'gid','desc':'','width':'8%','check':'1'}"); //checkbox 
		list.add(c1);
		JSONObject c2 = JSONObject.fromObject("{'name':'mouldcode','desc':'模具编码','width':'30%','check':'0'}");
		list.add(c2);
		JSONObject c3 = JSONObject.fromObject("{'name':'mouldname','desc':'模具名称','width':'','check':'0'}");
		list.add(c3);
		JSONObject c4 = JSONObject.fromObject("{'name':'mouldRatio','desc':'模比','width':'','check':'0'}");
		list.add(c4);
		
		return list;
	}

}
