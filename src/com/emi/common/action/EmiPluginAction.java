package com.emi.common.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cxf.binding.corba.wsdl.Array;

import net.sf.json.JSONObject;

import com.emi.common.bean.core.TreeNode;
import com.emi.common.bean.core.ITreeQuery;
import com.emi.common.bean.core.TreeType;
import com.emi.common.service.EmiPluginService;
import com.emi.common.service.tree.GoodsTreeQuery;
import com.emi.common.util.CommonUtil;
import com.emi.sys.core.bean.PageBean;
import com.emi.sys.core.format.EmiJsonArray;


/**
 * 文件上传插件
 * 2015年6月24日11:06:30
 */
public class EmiPluginAction extends BaseAction {
	private static final long serialVersionUID = 5873260984216825353L;
	private EmiPluginService emiPluginService;
	
	public EmiPluginService getEmiPluginService() {
		return emiPluginService;
	}


	public void setEmiPluginService(EmiPluginService emiPluginService) {
		this.emiPluginService = emiPluginService;
	}

	/**
	 * @category 单据分页的各个id
	 * 2015年12月15日 上午9:55:23 
	 * @author 朱晓陈
	 */
	@SuppressWarnings("rawtypes")
	public void getPageTurnIds(){
		try {
			String tableName = getParameter("tableName");//表名
			String idColumn = getParameter("idColumn");	//id的字段名
			String thisGid = getParameter("thisGid");	//当前数据的id
			String condition = getParameter("condition");	//其他过滤条件
//			JSONObject condition_json = JSONObject.fromObject(condition); 
			
			String pre_id = emiPluginService.getPrePageGid(tableName,idColumn,thisGid,condition);
			String next_id = emiPluginService.getNextPageGid(tableName,idColumn,thisGid,condition);
			String first_id = emiPluginService.getFirstPageGid(tableName,idColumn,thisGid,condition);
			String last_id = emiPluginService.getLastPageGid(tableName,idColumn,thisGid,condition);
			
			Map map = new HashMap();
			map.put("first_id", first_id);
			map.put("pre_id", pre_id);
			map.put("next_id", next_id);
			map.put("last_id", last_id);
			responseWrite(JSONObject.fromObject(map).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @category 选择插件主页面
	 * 2016年4月12日 下午1:28:47 
	 * @author zhuxiaochen
	 * @return
	 */
	public String selectMain(){
		setRequstAttribute("treeType", getParameter("treeType"));//类型 调用TreeType的静态变量
		setRequstAttribute("multi", getParameter("multi"));//是否可多选0/1，默认0单选 
		setRequstAttribute("showTree", getParameter("showTree"));//是否显示树 0/1，默认1显示
		setRequstAttribute("showList", getParameter("showList"));//是否显示列表 0/1，默认1显示
		setRequstAttribute("selectedId", getParameter("selectedId"));//已选择的id
		return "selectMain";
	}
	
	/**
	 * @category 选择插件左侧树
	 * 2016年4月12日 下午1:28:47 
	 * @author zhuxiaochen
	 * @return
	 */
	public String selectTree(){
		try {
			String type = getParameter("treeType");
			ITreeQuery treeQuery = emiPluginService.getTreeQuery(type,getRequest());
			if(treeQuery!=null){
				List<TreeNode> treeNodes = treeQuery.getQueryTree();
				for(TreeNode treeNode:treeNodes){
					if(CommonUtil.isNullString(treeNode.getPid()) || "0".equals(treeNode.getPid().trim())){
						treeNode.setPid("");
					}
				}
				TreeNode root = new TreeNode();
				root.setId("");
				root.setName("全部");
				treeNodes.add(root);
				
				setRequstAttribute("treeJson", EmiJsonArray.fromObject(treeNodes).toString());
			}
			setRequstAttribute("treeType", getParameter("treeType"));
			setRequstAttribute("multi", getParameter("multi"));
			setRequstAttribute("showList", getParameter("showList"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "selectTree";
	}
	
	/**
	 * @category 选择插件右侧列表
	 * 2016年4月12日 下午1:28:47 
	 * @author zhuxiaochen
	 * @return
	 */
	public String selectList(){
		try {
			String type = getParameter("treeType");
//			String gid = getParameter("id");
			ITreeQuery treeQuery = emiPluginService.getTreeQuery(type,getRequest());
			if(treeQuery!=null){
				PageBean bean = treeQuery.getQueryList();
				setRequstAttribute("data", bean);
				setRequstAttribute("columns", treeQuery.getColumns());
				
				//查找id column
				for(JSONObject jo : treeQuery.getColumns()){
					if("1".equals(CommonUtil.Obj2String(jo.get("check")))){
						setRequstAttribute("idColumn", CommonUtil.Obj2String(jo.get("name")));
					}
				}
			}
			setRequstAttribute("id", getParameter("id"));
			setRequstAttribute("treeType", getParameter("treeType"));
			setRequstAttribute("multi", getParameter("multi"));
			setRequstAttribute("showTree", getParameter("showTree"));
			setRequstAttribute("showList", getParameter("showList"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "selectList";
	}
	
	public void test(){
		try {
			emiPluginService.test();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	/**
	 * @category 选择库存数据
	 * 2016年4月12日 下午1:28:47 
	 * @author zhuxiaochen
	 * @return
	 */
	public String selectAllocationStockList(){
		try {
			int pageIndex = getPageIndex();	
			int pageSize = getPageSize();
			String goodsUid=getParameter("goodsUid");
			String goodsAllocationUid=getParameter("goodsAllocationUid");
			PageBean pb=emiPluginService.getAllocationStock(pageIndex, pageSize,goodsUid,goodsAllocationUid);
			setRequstAttribute("data", pb);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "selectAllocationStockList";
	}
	
	
}
