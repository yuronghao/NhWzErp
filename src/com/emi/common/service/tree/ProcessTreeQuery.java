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
 * @Title:工序
 * @Copyright: Copyright (c) v1.0
 * @Company: 江苏一米智能科技股份有限公司
 * @project name: LugErp
 * @author: zhuxiaochen
 * @version: V1.0
 * @time: 2016年4月22日 上午9:51:14
 */
public class ProcessTreeQuery extends BaseTreeQuery implements ITreeQuery{

	public ProcessTreeQuery(HttpServletRequest request) {
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
		PageBean pagebean = this.emiPluginDao.getProcessList( keyWord,sobId,orgId,this.pageIndex,this.pageSize,showTree);
		this.request.setAttribute("keyWord", keyWord);
		return pagebean;
	}

	@Override
	public List<JSONObject> getColumns() {
		List<JSONObject> list = new ArrayList<JSONObject>();
		//宽度百分比，注意已经有一列‘序号’，占了8%
		JSONObject c1 = JSONObject.fromObject("{'name':'gid','desc':'','width':'8%','check':'1'}"); //checkbox 
		list.add(c1);
		JSONObject c2 = JSONObject.fromObject("{'name':'opcode','desc':'工序编码','width':'40%','check':'0'}");
		list.add(c2);
		JSONObject c3 = JSONObject.fromObject("{'name':'opname','desc':'工序名称','width':'44%','check':'0'}");
		list.add(c3);
		JSONObject c4 = JSONObject.fromObject("{'name':'isCheck','desc':'是否质检','width':'','check':'0','hidde':'1'}");
		list.add(c4);
		JSONObject c5 = JSONObject.fromObject("{'name':'checkRate','desc':'质检合格率','width':'','check':'0','hidde':'1'}");
		list.add(c5);
		JSONObject c6 = JSONObject.fromObject("{'name':'isStock','desc':'是否入库','width':'','check':'0','hidde':'1'}");
		list.add(c6);
		JSONObject c7 = JSONObject.fromObject("{'name':'standardPrice','desc':'标准工价','width':'','check':'0','hidde':'1'}");
		list.add(c7);
		JSONObject c8 = JSONObject.fromObject("{'name':'isMustScanMould','desc':'是否必扫模具','width':'','check':'0','hidde':'1'}");
		list.add(c8);
		return list;
	}

}
