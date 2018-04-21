package com.emi.common.service.tree;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.emi.common.bean.core.ITreeQuery;
import com.emi.common.bean.core.TreeNode;
import com.emi.sys.core.bean.PageBean;

import net.sf.json.JSONObject;

/** 
* @author  作者 :张向龙    E-mail: 
* @date 创建时间：2017年6月9日 上午10:25:27 
* @version 1.0  
*/
public class RdStyleTreeQuery extends BaseTreeQuery implements ITreeQuery{

	public RdStyleTreeQuery(HttpServletRequest request) {
		super(request);
		// TODO 自动生成的构造函数存根
	}

	@Override
	public List<TreeNode> getQueryTree() {
		// TODO 自动生成的方法存根
		List<TreeNode> list = this.emiPluginDao.getRdStyleTree(sobId,orgId);
		return list;
		
	}

	@Override
	public PageBean getQueryList() {
		// TODO 自动生成的方法存根
		String showTree = this.request.getParameter("showTree");
		String keyWord = this.request.getParameter("keyWord");
		//String gid = this.request.getParameter("id");
		PageBean pagebean = this.emiPluginDao.getRdStyleList(this.pageIndex,this.pageSize,showTree,keyWord);
		this.request.setAttribute("keyWord", keyWord);
		return pagebean;
	}

	@Override
	public List<JSONObject> getColumns() {
		// TODO 自动生成的方法存根
		List<JSONObject> list = new ArrayList<JSONObject>();
		//宽度百分比，注意已经有一列‘序号’，占了8%
		JSONObject c1 = JSONObject.fromObject("{'name':'gid','desc':'','width':'8%','check':'1'}"); //checkbox 
		list.add(c1);
		JSONObject c2 = JSONObject.fromObject("{'name':'crdName','desc':'类别名','width':'30%','check':'0'}");
		list.add(c2);
		/*JSONObject c3 = JSONObject.fromObject("{'name':'userName','desc':'姓名','width':'','check':'0'}");
		list.add(c3);*/
		
		return list;
	}

}
